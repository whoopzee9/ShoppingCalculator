package com.example.shoppingcalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = 29, manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setLoginActivity() {
        loginActivity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void loginActivityShouldNotBeNull() {
        Assert.assertNotNull(loginActivity);
    }

}
