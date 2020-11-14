package com.example.testefinal.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.testefinal.R;
import com.example.testefinal.ui.info.paginas.PaginaAvancada;
import com.example.testefinal.ui.info.paginas.PaginaMedia;
import com.example.testefinal.ui.info.paginas.PaginasSimples;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    private InfoViewModel infoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        return root;
    }
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new PaginasSimples(), "Iniciante");
        adapter.addFragment(new PaginaMedia(), "Intermediário");
        adapter.addFragment(new PaginaAvancada(), "Avançado");
        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}