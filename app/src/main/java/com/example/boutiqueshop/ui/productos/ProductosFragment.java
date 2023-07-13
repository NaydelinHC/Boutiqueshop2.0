package com.example.boutiqueshop.ui.productos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.boutiqueshop.R;
import com.example.boutiqueshop.databinding.FragmentGalleryBinding;
import com.example.boutiqueshop.databinding.FragmentProductosBinding;
import com.example.boutiqueshop.ui.gallery.GalleryViewModel;

public class ProductosFragment extends Fragment {

    private FragmentProductosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProductosViewModel productosViewModel =
                new ViewModelProvider(this).get(ProductosViewModel.class);

        binding = FragmentProductosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProductos;
        productosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}