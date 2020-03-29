package com.frednm.baking_app.data.repository.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MutableLiveData<ResultType> result = new MutableLiveData<>();

    Executor executor = Executors.newSingleThreadExecutor();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(null);
        executor.execute(() -> {
            ResultType dbResult = loadFromDb();
            if (shouldFetch(dbResult)) {
                try {
                    fetchFromNetwork(dbResult);
                } catch (Exception e) {
                  //  setValue(Resource.error(e, loadFromDb()));
                }
            } else {
                setValue(dbResult);
            }
        });
    }

    private void fetchFromNetwork(final ResultType dbResult) {
        setValue(dbResult);
        createCallAsync().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                executor.execute(() -> {
                    saveCallResults(processResponse(response.body())); // Save result in DB
                    setValue(loadFromDb());//Take new data from DB
                });
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                // setValue(Resource.error(t, dbResult));
            }
        });
    }

    @MainThread
    private void setValue(ResultType newValue) { // Resource<ResultType>
        if (result.getValue() != newValue) result.postValue(newValue);
    }

    public final LiveData<ResultType> asLiveData() {
        return result;
    }

    @WorkerThread
    protected abstract ResultType processResponse(RequestType response);

    @WorkerThread
    protected abstract void saveCallResults(@NonNull ResultType items);

    @MainThread
    protected abstract Boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract ResultType loadFromDb();

    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCallAsync();

}