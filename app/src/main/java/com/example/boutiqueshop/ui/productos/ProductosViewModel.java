package com.example.boutiqueshop.ui.productos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductosViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ProductosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Productos");
    }

    public LiveData<String> getText() {
        return mText;
    }

}