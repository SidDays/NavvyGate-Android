package com.sidrk.travelentsearch.details;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sidrk.travelentsearch.FavoritesFragment;
import com.sidrk.travelentsearch.R;
import com.sidrk.travelentsearch.SearchFragment;
import com.sidrk.travelentsearch.details.info.PlaceInfoFragment;
import com.sidrk.travelentsearch.details.map.PlaceMapsFragment;
import com.sidrk.travelentsearch.details.photos.PlacePhotosFragment;
import com.sidrk.travelentsearch.details.reviews.PlaceReviewsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaceDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PlaceDetailsActivity";

    // default value for testing purposes - overwritten upon load
    private String placeDetailsJSON = "{\"html_attributions\":[],\"result\":{\"address_components\":[{\"long_name\":\"2527\",\"short_name\":\"2527\",\"types\":[\"street_number\"]},{\"long_name\":\"South San Pedro Street\",\"short_name\":\"S San Pedro St\",\"types\":[\"route\"]},{\"long_name\":\"South Los Angeles\",\"short_name\":\"South Los Angeles\",\"types\":[\"neighborhood\",\"political\"]},{\"long_name\":\"Los Angeles\",\"short_name\":\"Los Angeles\",\"types\":[\"locality\",\"political\"]},{\"long_name\":\"Los Angeles County\",\"short_name\":\"Los Angeles County\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"California\",\"short_name\":\"CA\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"United States\",\"short_name\":\"US\",\"types\":[\"country\",\"political\"]},{\"long_name\":\"90011\",\"short_name\":\"90011\",\"types\":[\"postal_code\"]},{\"long_name\":\"1519\",\"short_name\":\"1519\",\"types\":[\"postal_code_suffix\"]}],\"adr_address\":\"<span class=\\\"street-address\\\">2527 S San Pedro St</span>, <span class=\\\"locality\\\">Los Angeles</span>, <span class=\\\"region\\\">CA</span> <span class=\\\"postal-code\\\">90011-1519</span>, <span class=\\\"country-name\\\">USA</span>\",\"formatted_address\":\"2527 S San Pedro St, Los Angeles, CA 90011, USA\",\"formatted_phone_number\":\"(877) 883-9643\",\"geometry\":{\"location\":{\"lat\":34.0221597,\"lng\":-118.2610431},\"viewport\":{\"northeast\":{\"lat\":34.0234937802915,\"lng\":-118.2596603197085},\"southwest\":{\"lat\":34.0207958197085,\"lng\":-118.2623582802915}}},\"icon\":\"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\"id\":\"49efc10f9becaae83a263291a59a1c949bdc299d\",\"international_phone_number\":\"+1 877-883-9643\",\"name\":\"Domino's Pizza\",\"opening_hours\":{\"open_now\":true,\"periods\":[{\"close\":{\"day\":1,\"time\":\"0100\"},\"open\":{\"day\":0,\"time\":\"1000\"}},{\"close\":{\"day\":2,\"time\":\"0100\"},\"open\":{\"day\":1,\"time\":\"1000\"}},{\"close\":{\"day\":3,\"time\":\"0100\"},\"open\":{\"day\":2,\"time\":\"1000\"}},{\"close\":{\"day\":4,\"time\":\"0100\"},\"open\":{\"day\":3,\"time\":\"1000\"}},{\"close\":{\"day\":5,\"time\":\"0100\"},\"open\":{\"day\":4,\"time\":\"1000\"}},{\"close\":{\"day\":6,\"time\":\"0200\"},\"open\":{\"day\":5,\"time\":\"1000\"}},{\"close\":{\"day\":0,\"time\":\"0200\"},\"open\":{\"day\":6,\"time\":\"1000\"}}],\"weekday_text\":[\"Monday: 10:00 AM – 1:00 AM\",\"Tuesday: 10:00 AM – 1:00 AM\",\"Wednesday: 10:00 AM – 1:00 AM\",\"Thursday: 10:00 AM – 1:00 AM\",\"Friday: 10:00 AM – 2:00 AM\",\"Saturday: 10:00 AM – 2:00 AM\",\"Sunday: 10:00 AM – 1:00 AM\"]},\"photos\":[{\"height\":682,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAA8lMnlRVMj3EJ8HJkvJkHqN2TetYkXEMjhzaYTyQwWMd48R0TG4atHUGHEwbdXWYFCWxV0Q2TPfL_1jhwun2NihBodJj6Hch3LfbBeJ-RwtmGhaBEDomZSVEY6u1iVRR_EhBg1Y6xniCKGag5D7m5skEpGhTny9jx5wnGy9fk92XX81w4I9dllQ\",\"width\":1024},{\"height\":650,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAAHqYG7hcqFCjaWoXn4PYDWhh4VD6eJ_9s7MkNvbRJdCg-ZoxD1AztM8vQKAU3GBaytIf4E8k_ygOBCUmtNH4eSaBY4qAbpyDaqnf9m6tInlH1fnC4bN_jXAHmOuWzlalXEhBJ26OmGPjbFrhc3lM1jQxOGhQH6UU8MyjAAtn8nLm_ql3UpQ5N5A\",\"width\":650},{\"height\":768,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAAdZNmgl7n35jQuSIV80sZhAanx5STXun4wIlbCjzWcq4Hb3SkOxRR-P1KDuGg7krYjcYeinfDKYf9wq1rFyZgI6OMhXRE1q9JH5NAIB85L-sJ0kE5FJOPOHKXYasHRnJiEhCTVev2chzCIQ_vTSUUj_VDGhQVObSqU7nQ6ixBuIZ3oydnqG3-Hg\",\"width\":1024},{\"height\":768,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAANuYyrjTeypmYD2wvvDMSD0SsV-W11NpbB6r0xn6JA7Be4LqyyX7LK8RscMg4KZrWi1T8yFdamkVLGY43JBo-nyMSqpL9OuScLqva7-Lqdq1tt7oJ8ahOOqkfh7ATQN2kEhCUPli9QegxyC06txJYtW5TGhR49Je06ksJB_f-qkjVSH_vNlDhxA\",\"width\":1024},{\"height\":674,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAAelF2qYrdcgTtk3htJE1VNLYia-PFnns_vrltWHZAurNKqT5IDQYDcw5oDdTQ9e5lekepwNuyKtPNeQSpYi_ylYl9sL8RJQ0jwwY6AYdmFgKyD-7OpAr3VBmH2QR2PYgBEhB2ugXUcQTQ-TSsUbm2e3VqGhRgv8F8Cr2jguU1SybSTn0GmZAIPQ\",\"width\":1024},{\"height\":1128,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/101957760811806916067/photos\\\">Will Carrera</a>\"],\"photo_reference\":\"CmRaAAAAq5lFQGn5dLoNZSf_Qd7gwucR2XmaRh3G6x9lPMatLz6gs4wUm4LS49HE7RgfzRqmzgubdVcrr4qiKu7kTqBnz34JSkCe5k0R79F_xKeiLDbGr3ykmez6RHTO1TMzge1BEhAPGw4VDE2lRPiTlJK50p2ZGhQLCLoziS8fMfEIN6UHErHA9QTH3A\",\"width\":1440},{\"height\":1128,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/101957760811806916067/photos\\\">Will Carrera</a>\"],\"photo_reference\":\"CmRaAAAAFQpoei9KqjFXaUbNC2-KftDll0r582GTMqL0wV5ApGV8yHXuyeRlDqdbcoU6FuMRqPu5T1XjiKvmuNitTmEsFm3TlHg4d4pr5dGYXgeZFapdLZgQC06DSEWOXr1DSBfeEhDTegE1KujbgG4AIR7bNnLAGhSftVM5-JGFCIpDLcenYhxosaVvVQ\",\"width\":1440},{\"height\":1128,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/101957760811806916067/photos\\\">Will Carrera</a>\"],\"photo_reference\":\"CmRaAAAApZ2Fo4Nmtj2b26wI73VYL3K-PV-kGRIVgWCX498cAR-wheVDVCIwK6xDxPNywUO2LAYAYs9wuJ6RtGbuw1TUtVtfqBcc2uO7nR8Y-UjmtgV-oyID2im5z77OZ2t_xhELEhDyCBK1jwozx1VYgRY6elUAGhTiq_9vnTnF9OpJdJ7YIffQaY00Sg\",\"width\":1440},{\"height\":682,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105181536616651996000/photos\\\">Domino&#39;s Pizza</a>\"],\"photo_reference\":\"CmRaAAAAJ64p_CyHz9ls6kCGs2LOOR6udbxn_LrVinxIAjCBp8g7SnMUJaNvvngh72PpYC4oVRmq6LaxOtDXu2rMO3xEhpjGmNMH9wFO_h5lRHmJkLliFjzsQ7ByQj74hIBVkl6vEhBgh8EtBi_ylmdVNoGbAG52GhSFW5ttiyXApTgUWlEcNhuLxKIZig\",\"width\":1024},{\"height\":1080,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/103738676722350000362/photos\\\">Ernesto de jesus berumen velarde</a>\"],\"photo_reference\":\"CmRaAAAArZFiasJnf0Wug0EJL7mEMQmIWTDJAMgxIYUK--iSg1w95QWHIlfEGKGt04fyCPScROAup0Ws02851I9_bpM2obZBTMupPSZLroNXDkrMtNLM9Dy8TT5PdrNuD7cmrm1dEhA_Aq1nzoT4m9u0FucjGfiCGhS8KGwZgMZE9DbaoNtv7dZCS8_DyQ\",\"width\":1920}],\"place_id\":\"ChIJk4QshHjIwoARtGhnQtLg9hQ\",\"rating\":2.5,\"reference\":\"CmRRAAAAk0tNXGn7Yam7dPDsr1FX5FAy404AweghMDgkc4AngOHma9dX9imCswa0p3wSV_J1iZcW0-rcZ6Ld9wfxQN87jdS-2aXGRJ7lg7x4qgNuOdisjgIY8y4yu_vBcXnYrvkrEhBRb6w7q4IWIZ_Om1Ba8iFaGhTn-6my5BFdOZ5x5g4x6BAf6entVw\",\"reviews\":[{\"author_name\":\"Oswaldo Zuniga\",\"author_url\":\"https://www.google.com/maps/contrib/116599616307281516464/reviews\",\"language\":\"en\",\"profile_photo_url\":\"https://lh5.googleusercontent.com/-7JzkdHuBzY4/AAAAAAAAAAI/AAAAAAAAAAA/ACLGyWBL_LrwNvYAhaG1_RFGu_IldiAd6w/s128-c0x00000000-cc-rp-mo/photo.jpg\",\"rating\":1,\"relative_time_description\":\"a month ago\",\"text\":\"Ordered from this location online never had a problem before with delivery but today i placed an order online looked at the tracker and it said it was delivered but I never received the pizza, after a 2hrs plus wait I called and they told me to wait another 5 to 10 min. After waiting 30 min I called again and they couldn’t give me reason why it never got delivered until I spoke with the manager daisy and told me the driver called and knock but there was no call or knock. Since the driver said she allegedly called and knocked but no one answered they had to cancel my order.\",\"time\":1517796976},{\"author_name\":\"Miguel Paz\",\"author_url\":\"https://www.google.com/maps/contrib/115101063796797616835/reviews\",\"language\":\"en\",\"profile_photo_url\":\"https://lh5.googleusercontent.com/-40-6pmWM85c/AAAAAAAAAAI/AAAAAAAAAKQ/DOF37b2_kjM/s128-c0x00000000-cc-rp-mo/photo.jpg\",\"rating\":1,\"relative_time_description\":\"4 weeks ago\",\"text\":\"Cold Pasta. seriously, cold pasta. Their pizza making skills are terrible. This store gives Dominoes a bad name.\",\"time\":1520115587},{\"author_name\":\"Johnny Hernandez\",\"author_url\":\"https://www.google.com/maps/contrib/112654196124281924054/reviews\",\"language\":\"en\",\"profile_photo_url\":\"https://lh3.googleusercontent.com/-yj-wmgWCjjQ/AAAAAAAAAAI/AAAAAAAAAAA/ACLGyWBgx5UZwsa33C_JTvPY7e8-Yy9lOg/s128-c0x00000000-cc-rp-mo/photo.jpg\",\"rating\":1,\"relative_time_description\":\"a month ago\",\"text\":\"I live 4 blocks away, not even a 5 minute drive, why is it that the tracker says it'll take 20-30 mins and it has been over an hour, they even told me the driver left the store 25 minutes ago, this is ridiculous. I don't mind waiting extra here and there but I'm not about to pay for cold food. So much for having a dominos store nearby.\",\"time\":1519953067},{\"author_name\":\"Jordan Mendoza\",\"author_url\":\"https://www.google.com/maps/contrib/109002931908282014513/reviews\",\"language\":\"en\",\"profile_photo_url\":\"https://lh4.googleusercontent.com/-sN2g7ZQZCzM/AAAAAAAAAAI/AAAAAAAAABs/VNR-fPJdbcc/s128-c0x00000000-cc-rp-mo/photo.jpg\",\"rating\":1,\"relative_time_description\":\"a month ago\",\"text\":\"People please don't waste your time buying from there. Altho they made our pizzas fast, the quality of them were horrible. The pizzas were burned, really dry, and not to mention the people working there are so unprofessional. I don't know who's hiring those people. Non the less their paying machines don't even work. Never ordering from them again.\",\"time\":1518675637},{\"author_name\":\"Clara Rose\",\"author_url\":\"https://www.google.com/maps/contrib/107553622106700364596/reviews\",\"language\":\"en\",\"profile_photo_url\":\"https://lh5.googleusercontent.com/-g_-YScLBCyk/AAAAAAAAAAI/AAAAAAAADWY/5JurhSGykjk/s128-c0x00000000-cc-rp-mo-ba2/photo.jpg\",\"rating\":5,\"relative_time_description\":\"3 months ago\",\"text\":\"Best Dominos pizza! Made excellent and fast! Employees were welcoming, polite, friendly and respectful. Thank you see you soon\",\"time\":1513975143}],\"scope\":\"GOOGLE\",\"types\":[\"meal_delivery\",\"meal_takeaway\",\"restaurant\",\"food\",\"point_of_interest\",\"establishment\"],\"url\":\"https://maps.google.com/?cid=1510641918691207348\",\"utc_offset\":-420,\"vicinity\":\"2527 South San Pedro Street, Los Angeles\",\"website\":\"https://www.dominos.com/en/?redirect=homepage&utm_source=google&utm_medium=loclist&utm_campaign=localmaps\"},\"status\":\"OK\"}";

    // default value for testing purposes - overwritten upon load
    private String yelpJSON = "[{\"id\":\"4P5Fb5F2Tp44OMJ2aK9wsA\",\"url\":\"https://www.yelp.com/biz/baggios-pizza-fort-lee?hrid=4P5Fb5F2Tp44OMJ2aK9wsA&adjust_creative=GZCgF3LKdEkgco10_S9Hvg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_reviews&utm_source=GZCgF3LKdEkgco10_S9Hvg\",\"text\":\"Baggios Pizza is a solid pizza joint. Had the special which consist of two slices and a can of soda for $5. Great deal. I think you can order ant time of...\",\"rating\":4,\"time_created\":\"2018-03-19 14:17:05\",\"user\":{\"image_url\":\"https://s3-media2.fl.yelpcdn.com/photo/t1Weo_610IsaMY2nW4zWhw/o.jpg\",\"name\":\"Billy V.\"}},{\"id\":\"hfit7E_U9lwhm43IaGDfCA\",\"url\":\"https://www.yelp.com/biz/baggios-pizza-fort-lee?hrid=hfit7E_U9lwhm43IaGDfCA&adjust_creative=GZCgF3LKdEkgco10_S9Hvg&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_reviews&utm_source=GZCgF3LKdEkgco10_S9Hvg\",\"text\":\"Before reading my review, please know that I have worked in the food service industry for 3 years :\\n\\nI begged my mom to take me here every time we are in...\",\"rating\":1,\"time_created\":\"2017-11-25 18:50:15\",\"user\":{\"image_url\":\"https://s3-media1.fl.yelpcdn.com/photo/2S1jf9eE35tWMtmprqjcdg/o.jpg\",\"name\":\"Rachel P.\"}}]";

    // Additional convenience fields
    private String placeName = "Test place";
    private String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";
    private double placeLat = 34.007889, placeLng = -118.2585096; // USC
    private Toolbar mActionBarToolbar;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        try {
            // TODO: GET JSON RESPONSE
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                placeDetailsJSON = bundle.getString("resultJSON");
            }
            JSONObject json = new JSONObject(placeDetailsJSON);

            JSONObject result = json.getJSONObject("result");

            placeName = result.getString("name");
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mActionBarToolbar);
            getSupportActionBar().setTitle(placeName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            placeId = result.getString("place_id");
            JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
            placeLat = location.getDouble("lat");
            placeLng = location.getDouble("lng");

        } catch (JSONException e) {
            Log.e(TAG, "Invalid place details response!");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {

                case 1:
                    return PlacePhotosFragment.newInstance(placeId);

                case 2:
                    return PlaceMapsFragment.newInstance(placeName, placeLat, placeLng);

                case 3:
                    return PlaceReviewsFragment.newInstance(placeDetailsJSON, yelpJSON);

                default:
                    return PlaceInfoFragment.newInstance(placeDetailsJSON);

            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }
}
