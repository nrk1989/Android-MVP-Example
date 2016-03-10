/**
 * HomeFragment.java
 * <p/>
 * A fragment for the home screen.
 *
 * @package com.raj.task.ui.fragment
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
package com.raj.task.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raj.task.R;
import com.raj.task.contract.HomeContract;
import com.raj.task.data.model.Device;
import com.raj.task.presenter.HomePresenter;
import com.raj.task.ui.adapter.DevicesListAdapter;

import java.util.List;

/**
 * An activity for the home screen of the application.
 */
public class HomeFragment extends Fragment {

    // UI Widgets
    private View mRootView;
    private Toolbar mToolbar;
    private RecyclerView mDevicesListView;
    private SwipeRefreshLayout mRefreshLayout;

    private HomeContract.UserActionsListener mUserActionsListener;

    private DevicesListAdapter listAdapter;


    /**
     * The home screen contact view.
     */
    private HomeContract.View mHomeView = new HomeContract.View() {
        @Override
        public void setProgressIndicator(boolean indicator) {
            mRefreshLayout.setRefreshing(indicator);
        }

        @Override
        public void showInfoToast(String message) {
            Snackbar.make(mRootView, message, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void showRetryToast(String message) {
            Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action_retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mUserActionsListener.loadDeviceData();
                        }
                    }).show();
        }

        @Override
        public void updateList(List<Device> devices) {
            mRefreshLayout.setRefreshing(false);
            if (devices != null) {
                listAdapter = new DevicesListAdapter(getActivity(), devices);
                mDevicesListView.setAdapter(listAdapter);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentConfiguration();

        // Initializes the user action listener
        mUserActionsListener = new HomePresenter(getActivity(), mHomeView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return mRootView;
    }

    /**
     * Initializes the views and other local members in this context.
     */
    private void initViews() {
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        setupToolBar();

        mDevicesListView = (RecyclerView) mRootView.findViewById(R.id.recyclerViewDevice);
        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refreshLayout);

        // Setup the floating action button
        FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserActionsListener.loadDeviceData();
            }
        });

        // Setup the swipe refresh listener
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mUserActionsListener.loadDeviceData();
            }
        });

        // Setup the recycler view
        mDevicesListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (listAdapter != null) {
            mDevicesListView.setAdapter(listAdapter);
        }
    }

    /**
     * Setup the toolbar and its menu.
     */
    private void setupToolBar() {
        mToolbar.setTitle(getString(R.string.get_devices));
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }


    /**
     * Setup the fragment configuration.
     */
    public void setupFragmentConfiguration() {
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }
}
