/**
 * HomeActivity.java
 * <p/>
 * An activity for the home screen of the application.
 *
 * @package com.raj.task.ui.activity
 * @version 1.0
 * @author Rajkumar.N
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */

package com.raj.task.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raj.task.ui.fragment.HomeFragment;

/**
 * An activity for the home screen of the application.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag("content") == null) {

            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new HomeFragment(), "content").commit();
        }
    }

}
