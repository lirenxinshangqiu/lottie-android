package com.airbnb.lottie;

import android.Manifest;
import android.content.res.Resources;
import android.os.Build;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.TypedValue;

import com.airbnb.happo.HappoRunner;
import com.airbnb.lottie.samples.MainActivity;
import com.airbnb.lottie.samples.TestColorFilterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Run these with: ./gradlew recordMode screenshotTests
 * If you run that command, it completes successfully, and nothing shows up in git, then you
 * haven't broken anything!
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LottieTest {
  @Rule public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
      MainActivity.class);

  @Rule public ActivityTestRule<TestColorFilterActivity> colorFilterActivityRule =
      new ActivityTestRule<>(TestColorFilterActivity.class);

  @Rule
  public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE
  );

  @Test public void testAll() {
    HappoRunner.runTests(
        mainActivityRule.getActivity(),
        new LottieSnapshotProvider(mainActivityRule.getActivity()),
        com.airbnb.lottie.samples.BuildConfig.S3AccessKey,
        com.airbnb.lottie.samples.BuildConfig.S3SecretKey,
        "lottie-happo",
        com.airbnb.lottie.samples.BuildConfig.HappoApiKey,
        com.airbnb.lottie.samples.BuildConfig.HappoSecretKey,
        "lottie",
        com.airbnb.lottie.samples.BuildConfig.GIT_BRANCH,
        com.airbnb.lottie.samples.BuildConfig.GIT_SHA,
        com.airbnb.lottie.BuildConfig.VERSION_NAME,
        "android" + Build.VERSION.SDK_INT);
  }

  private int dpToPx(int dp) {
    Resources resources = mainActivityRule.getActivity().getResources();
    return (int) TypedValue.applyDimension(1, (float) dp, resources.getDisplayMetrics());
  }
}
