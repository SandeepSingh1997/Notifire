package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class JoinedOwned extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;

    private Joined joinedFragment;
    private Owned ownedFragment;
    private Button logout;

    private FirebaseAuth firebaseAuth;

    private TextView userIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_owned);

        tablayout =(TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        logout = (Button) findViewById(R.id.logout);
        userIdText = (TextView)findViewById(R.id.userID);

        firebaseAuth = FirebaseAuth.getInstance();

        //Fragments
        joinedFragment = new Joined();
        ownedFragment = new Owned();

        tablayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(joinedFragment, "joined");
        viewPagerAdapter.addFragment(ownedFragment, "owned");
        viewPager.setAdapter(viewPagerAdapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(JoinedOwned.this, LoginActivity.class));
                finish();
            }
        });
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmenttitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment( Fragment fragment, String title){
            fragments.add(fragment);
            fragmenttitle.add(title) ;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmenttitle.get(position);
        }
    }
}