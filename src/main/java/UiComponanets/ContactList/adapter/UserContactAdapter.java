package UiComponanets.ContactList.adapter;

/**
 * Created by ddopi on 7/9/2017.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.networkmodule.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import UiComponanets.ContactList.viewHolder.UserViewHolder;
import UiComponanets.ContactList.util.RoundImage;


/**
 * Created by Trinity Tuts on 10-01-2015.
 */
public class UserContactAdapter extends BaseAdapter {

    public List<UserViewHolder> _data;
    private ArrayList<UserViewHolder> arraylist;
    Context _c;
    ViewHolder v;
    RoundImage roundedImage;

    public UserContactAdapter(List<UserViewHolder> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<UserViewHolder>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.contact_info, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.title = (TextView) view.findViewById(R.id.name);
        v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final UserViewHolder data = (UserViewHolder) _data.get(i);
        v.title.setText(data.getName());
        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.image);
            }
            // Seting round image
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.image); // Load default image
            roundedImage = new RoundImage(bm);
            v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());

        /*// Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });*/

        view.setTag(data);
        return view;
    }

    // Filter Class
    /// arraylist its a secondary array that hold  original  arrayList the adapter first initiated
    /// _data it is the dynamic array need to be cleared ant initialized to notify the adapter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (UserViewHolder wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
//
//    search = (SearchView) mainView.findViewById(R.id.searchView);
//    //*** setOnQueryTextListener ***
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//            // TODO Auto-generated method stub
//
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//            // TODO Auto-generated method stub
//            adapter.filter(newText);
//            return false;
//        }
//    });


    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        CheckBox check;
    }
}