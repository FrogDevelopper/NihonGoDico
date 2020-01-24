package fr.frogdevelopment.nihongo.dico.ui.bottom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.List;

import fr.frogdevelopment.nihongo.dico.R;
import fr.frogdevelopment.nihongo.dico.data.rest.RestServiceFactory;
import fr.frogdevelopment.nihongo.dico.data.rest.search.Entry;
import fr.frogdevelopment.nihongo.dico.data.search.SearchViewModel;
import fr.frogdevelopment.nihongo.dico.databinding.SearchsheetFragmentBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_OK;

public class BottomSheetSearchFragment extends BottomSheetDialogFragment {

    private SearchViewModel mViewModel;
    private SearchsheetFragmentBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.SearchBottomSheetDialog);

        mViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = SearchsheetFragmentBinding.inflate(getLayoutInflater());

        mBinding.bottomSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void search(String query) {
        mViewModel.isSearching(true);
        RestServiceFactory.getEntriesClient().search("eng", query).enqueue(new Callback<List<Entry>>() {
            @Override
            public void onResponse(@NonNull Call<List<Entry>> call, @NonNull Response<List<Entry>> response) {

                if (response.code() != HTTP_OK) {
                    mViewModel.setError("Response code : " + response.code());
                } else {
                    mViewModel.setEntries(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Entry>> call, @NonNull Throwable t) {
                Log.e("NIHONGO_DICO", "Error while searching", t);
                mViewModel.setError("Failure: " + ExceptionUtils.getMessage(t));
            }
        });
        dismiss();
    }
}