package example.com.vishwamedi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.ExpandCollapseController;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.ExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableList;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.vishwamedi.model.CasePostModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable
{
    private ArrayList<CasePostModel> list;
    private FragmentManager fragmentManager;
    Activity activity;
    public HomeAdapter(FragmentActivity activity, ArrayList<CasePostModel> list, FragmentManager supportFragmentManager) {

        this.activity=activity;
        this.list=list;
        fragmentManager=supportFragmentManager;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.case_header_layout,parent,false);

        ViewHolder holder=new ViewHolder(v);

        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, final int position) {

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CasDetailFragment fragment=new CasDetailFragment();

                Bundle bundle=new Bundle();

                bundle.putString("Status",list.get(position).getStatus());
                bundle.putString("TrackID",list.get(position).getTrackId());
                bundle.putString("CompanyName",list.get(position).getCompanyName());
                bundle.putString("Patient",list.get(position).getPatientName());
                bundle.putString("Hospital",list.get(position).getHospitalName());
                bundle.putString("Phone",list.get(position).getPhoneNo());
                bundle.putString("Doc",list.get(position).getTreatingDoc());
                bundle.putString("Addr",list.get(position).getAddress());
                bundle.putString("Land",list.get(position).getLandmark());
                bundle.putString("State",list.get(position).getState());
                bundle.putString("Dist",list.get(position).getDistrict());
                bundle.putString("Pin",list.get(position).getPincode());
                bundle.putString("Vendor",list.get(position).getVendor());
                bundle.putString("CaseDate",list.get(position).getCaseDate());
                bundle.putString("AdmissionDate",list.get(position).getDateOfAdmission());
                bundle.putString("Remark",list.get(position).getRemark());
                bundle.putString("Verifier",list.get(position).getVerifier());
                bundle.putString("DateTime",list.get(position).getDateTime());



                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .add(fragment,"DetailCaseFragment").addToBackStack("DetailCaseFragment");


                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.MainLayout, fragment);
                fragmentTransaction.commit();
            }
        });

        holder.datetime.setText(list.get(position).getDateTime());
        holder.address.setText(list.get(position).getHospitalName()+", "+list.get(position).getLandmark()+", "+list.get(position).getAddress()+", "+list.get(position).getPincode());

        holder.call.setTag(list.get(position).getPhoneNo());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("PrivateApi")
            @Override
            public void onClick(View v) {

                Intent callIntent = null;
                try {
                    callIntent = new Intent(activity, Class.forName(Intent.ACTION_CALL));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                callIntent.setData(Uri.parse(list.get(position).getPhoneNo()));

                if (ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
                activity.startActivity(callIntent);
            }
        });



        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView datetime,address;
        CardView card;
        ImageView call,location;
        public ViewHolder(View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.CaseCard);
            datetime=itemView.findViewById(R.id.CaseHeadDateTime);
            address=itemView.findViewById(R.id.CaseHeadAddr);

            call=itemView.findViewById(R.id.Call);
            location=itemView.findViewById(R.id.LocationMarker);
        }
    }
}
