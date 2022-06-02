package com.zoom.happiestplacesrestaurant.ui.order;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.ListItemOrdersBinding;
import com.zoom.happiestplacesrestaurant.model.Checkout;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.repository.OrderRepository;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.AppUtils;
import com.zoom.happiestplacesrestaurant.util.DateUtil;
import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;


import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    ListItemOrdersBinding mBinding;
    Context mContext;
    String orderStatus;
    private List<Order> mOrderList;
    OrderRepository orderRepository;

    public OrdersAdapter(Context context, String status, OrderRepository repo) {
        mContext = context;
        orderStatus = status;
        orderRepository = repo;
    }


    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = ListItemOrdersBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new OrdersViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Order OrderItem = mOrderList.get(position);
        holder.bind(OrderItem);
    }

    @Override
    public int getItemCount() {
        if (mOrderList == null) {
            return 0;
        }
        return mOrderList.size();
    }

    public void setList(List<Order> OrderList) {
        mOrderList = OrderList;
        notifyDataSetChanged();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private ListItemOrdersBinding mBinding;

        public OrdersViewHolder(@NonNull ListItemOrdersBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            if (!orderStatus.equals(AppConstants.Status.Paid.toString()))
                mBinding.status.setOnClickListener(this);
            //binding.getRoot().findViewById(R.id).setOnClickListener(this);
        }

        public void bind(Order orderItem) {
            mBinding.customer.setVisibility(VISIBLE);
            if (orderItem.getCustomer() != null)
                mBinding.customer.setText("By " + orderItem.getCustomer().getName());
            else
                mBinding.customer.setVisibility(GONE);
          //  if (orderItem.getCustomer() != null)
           //     mBinding.customer.setText("By " + orderItem.getCustomer().getName());
            if (orderItem.getTable() != null)
                mBinding.tableValue.setText(orderItem.getTable().getNumber());
            if (orderItem.getTotal_price() != null)
                mBinding.totalPrice.setText(
                        AppConstants.CURRENCY + " " + orderItem.getTotal_price().toString());
            if (orderItem.getStatus() != null)
                mBinding.status.setText(orderItem.getStatus());
            if (orderStatus.equals(AppConstants.Status.Paid.toString()))
                mBinding.status.setEnabled(false);
            if (orderItem.getTime() != null)
                mBinding.time.setText(DateUtil.getTimeDateFormat(orderItem.getTime()));
            mBinding.dishes.setText("");
            String s = "";
            for (int i = 0; i < orderItem.getOrderDishResponses().size(); i++) {
                String m = orderItem.getOrderDishResponses().get(i).getMenuItem().getName();
                if (m.length() > 17)
                    m = m.substring(0, 17);
                // String s=orderItem.getOrderDishResponses().get(i).getMenuItem().getName()+" X " +orderItem.getOrderDishResponses().get(i).getQty()+"\n";
                // mBinding.dishes.append(s);
                if (i == (orderItem.getOrderDishResponses().size() - 1)) {
                    s = m + " X " + orderItem.getOrderDishResponses().get(i).getQty();
                } else {
                    s = m + " X " + orderItem.getOrderDishResponses().get(i).getQty() + "\n";
                }
                mBinding.dishes.append(s);
            }
        }

        @Override
        public void onClick(View view) {
            //Dont show Paid option if not user
            Order order = mOrderList.get(getAdapterPosition());
            String[] statusArray=new String[]{};
            if(AppUtils.checkUserAccessCheckout(mContext))
                statusArray=SharedPrefUtils.getUser(mContext).getOrder_statusOwner();
            else
                statusArray=SharedPrefUtils.getUser(mContext).getOrder_statusOthers();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(mContext.getString(R.string.change_status));
            String[] finalStatusArray = statusArray;
            builder.setItems(statusArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Marked as paid
                    MutableLiveData<Order> orderLiveData = new MutableLiveData<>();

                   // if (finalStatusArray[which].equals(AppConstants.Status.Paid.toString()))
                     //   showRedeemDialog(orderLiveData);
                    //else {
                        orderRepository.markStatus(order.getId(),
                                new Checkout(finalStatusArray[which], 0), mContext, orderLiveData);
                        mBinding.status.setText(finalStatusArray[which]);
                    //}
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}



