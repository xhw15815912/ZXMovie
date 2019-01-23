package movie.bw.com.movie.frag;

import android.support.v4.app.Fragment;

import java.util.HashMap;



/**
 * 作者：夏洪武
 * 时间：
 * 邮箱：
 * 说明：
 */
public class FragmentFactory {
    private static HashMap<Integer, Fragment> fragments;

    public static Fragment getFragment(int position) {
        fragments = new HashMap<Integer, Fragment>();
        Fragment fragment = fragments.get(position);
        if (fragment != null) {
            return fragment;
        } else {

            switch (position) {
                case 0:
                    fragment = new MovieFrag();
                    break;
                case 1:
                    fragment = new CinemaFragment();
                    break;
                case 2:
                    fragment = new MyFrag();
                    break;
                default:
                    break;
            }
            fragments.put(position, fragment);
            return fragment;
        }

    }

}
