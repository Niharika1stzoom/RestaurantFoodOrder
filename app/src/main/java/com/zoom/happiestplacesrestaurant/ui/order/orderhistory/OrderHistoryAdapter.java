package com.zoom.happiestplacesrestaurant.ui.order.orderhistory;

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

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    ListItemOrdersBinding mBinding;
    Context mContext;
    private List<Order> mOrderList;
    OrderRepository orderRepository;

    public OrderHistoryAdapter(Context context,OrderRepository repo) {
        mContext = context;
        orderRepository = repo;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = ListItemOrdersBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new OrderHistoryViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
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

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private ListItemOrdersBinding mBinding;

        public OrderHistoryViewHolder(@NonNull ListItemOrdersBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.status.setOnClickListener(this);
        }

        public void bind(Order orderItem) {

            mBinding.customer.setVisibility(VISIBLE);
            if (orderItem.getCustomer() != null)
                mBinding.customer.setText("By " + orderItem.getCustomer().getName());
            else
                mBinding.customer.setVisibility(GONE);
            if (orderItem.getTable() != null)
                mBinding.tableValue.setText(orderItem.getTable().getNumber());
            if (orderItem.getTotal_price() != null)
                mBinding.totalPrice.setText(
                        AppConstants.CURRENCY + " " + orderItem.getTotal_price().toString());
            if (orderItem.getStatus() != null)
                mBinding.status.setText(orderItem.getStatus());
            if (orderItem.getStatus().equals(AppConstants.Status.Paid.toString()))
                mBinding.status.setEnabled(false);
            else
                mBinding.status.setEnabled(true);
            if (orderItem.getTime() != null)
                mBinding.time.setText(DateUtil.getTimeDateFormat(orderItem.getTime()));
            mBinding.dishes.setText("");
            String s = "";
            for (int i = 0; i < orderItem.getOrderDishResponses().size(); i++) {
                String m = orderItem.getOrderDishResponses().get(i).getMenuItem().getName();
                if (m.length() > 17)
                    m = m.substring(0, 17);
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
            String[] statusArray=new String[]{};
            if(AppUtils.checkUserAccessCheckout(mContext))
                statusArray=AppConstants.statusArray;
            else
                statusArray=AppConstants.staffStatusArray;
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(mContext, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background);
            builder.setTitle(mContext.getString(R.string.change_status));
            builder.setItems(statusArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Marked as paid
                    MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
                    Order order = mOrderList.get(getAdapterPosition());
                    if (AppConstants.statusArray[which].equals(AppConstants.Status.Paid.toString()))
                        showRedeemDialog(orderLiveData);
                    else {
                        orderRepository.markStatus(order.getId(), new Checkout(AppConstants.statusArray[which], 0), mContext, orderLiveData);
                        mBinding.status.setText(AppConstants.statusArray[which]);
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        private void showRedeemDialog(MutableLiveData orderLiveData) {
            EditText editText = new EditText(mContext);
            Order order = mOrderList.get(getAdapterPosition());
            editText.setText("0");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            String message = "";
            if (order.getCustomer() != null) {
                if (order.getCustomer().getName() != null) {
                    message += order.getCustomer().getName() + "\n";
                }
                message += order.getCustomer().getEmailId() + "\n" + "Redeemable Points: " + order.getCustomer().getCurrent_pts();
            }
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            AlertDialog dialog = new MaterialAlertDialogBuilder(mContext)
                    .setTitle(mContext.getString(R.string.redeem))
                    .setMessage(message)
                    .setView(editText)
                    .setPositiveButton(R.string.checkout, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String editTextInput = editText.getText().toString();
                            if (editTextInput.length() == 0)
                                editTextInput = "0";
                            orderRepository.markStatus(order.getId(), new Checkout(
                                            AppConstants.Status.Paid.toString(),
                                            Integer.parseInt(editTextInput))
                                    , mContext, orderLiveData);
                            mBinding.status.setText(AppConstants.Status.Paid.toString());
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }
    }
}

