package com.visium.fieldservice.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.util.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ServiceOrderDialogFragment extends AppCompatDialogFragment {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static List<Post> mPosts;
    private static ServiceOrder mOrder;
    private static ServiceOrderDetails mOrderDetails;
    private static ServiceOrderDialogListener mListener;
    private static EditText mFinishDate;
    private static Button mSyncButton;
    private static Button mPublishButton;
    private static Button mSaveButton;
    private static Button mPostsButton;
    private static Button mActionFinishDate;
    private static EditFinishDateAction mEditFinishDateAction;
    private static DeleteFinishDateAction mDeleteFinishDateAction;

    public static AppCompatDialogFragment newInstance(ServiceOrderDialogListener listener, ServiceOrderDetails orderDetails, List<Post> posts) {
        ServiceOrderDialogFragment.mOrderDetails = orderDetails;
        ServiceOrderDialogFragment.mListener = listener;
        mPosts = posts;
        return new ServiceOrderDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service_order, container, false);

        mFinishDate = (EditText) view.findViewById(R.id.order_finish_date);
        mActionFinishDate = (Button) view.findViewById(R.id.action_button);
        mSyncButton = (Button) view.findViewById(R.id.sync);
        mPublishButton = (Button) view.findViewById(R.id.publish);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mPostsButton = (Button) view.findViewById(R.id.posts_button);
        mActionFinishDate.requestFocus();
        mEditFinishDateAction = new EditFinishDateAction();
        mDeleteFinishDateAction = new DeleteFinishDateAction();
        this.updateFinishDateStatus();

        ((TextView) view.findViewById(R.id.order_title))
                .setText(getString(R.string.dialog_service_order_title, mOrderDetails.getNumber()));

        ((TextView) view.findViewById(R.id.order_start_date))
                .setText(DATE_FORMAT.format(mOrderDetails.getStartDateTime()));

        ((TextView) view.findViewById(R.id.order_user))
                .setText(mOrderDetails.getUserName());

        ((TextView) view.findViewById(R.id.order_posts_number))
                .setText(String.valueOf(mOrderDetails.getTotalPosts()));

        int qtdFinalizados = 0;
        if(mPosts != null) {
            for(Post p : mPosts) {
                if(p.isClosed()) {
                    qtdFinalizados++;
                }
            }
            ((TextView) view.findViewById(R.id.order_finished_posts_number))
                    .setText(String.valueOf(qtdFinalizados));
        } else {
             view.findViewById(R.id.order_finished_posts_number_layout).setVisibility(View.GONE);
        }

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
                mListener.onServiceOrderSaveButtonClicked(mOrderDetails, mPosts);
            }
        });

        mPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
                mListener.onServiceOrderPostsButtonClicked(mOrderDetails);
            }
        });

        mSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
                FileUtils.downloadServiceOrder(mOrderDetails.getId());
            }
        });

        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
                mListener.onServiceOrderPublishButtonClicked(mOrderDetails);
            }
        });

        updateStatus();

        return view;
    }

    private void updateStatus() {
        if (FileUtils.serviceOrderFileExists(mOrderDetails.getId())) {
            mSyncButton.setVisibility(View.GONE);
            mPublishButton.setVisibility(View.VISIBLE);
            mActionFinishDate.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.VISIBLE);
            mPostsButton.setVisibility(View.VISIBLE);
        } else {
            mSyncButton.setVisibility(View.VISIBLE);
            mPublishButton.setVisibility(View.GONE);
            mActionFinishDate.setVisibility(View.GONE);
            mSaveButton.setVisibility(View.GONE);
            mPostsButton.setVisibility(View.GONE);
        }
    }

    private void updateFinishDateStatus() {
        if (mOrderDetails.getFinishDateTime() != null) {
            mFinishDate.setText(DATE_FORMAT.format(mOrderDetails.getFinishDateTime()));
            mActionFinishDate.setText(R.string.dialog_service_order_finish_open);
            mActionFinishDate.setTextColor(getResources().getColor(R.color.white));
            mActionFinishDate.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selector_red_button));
            mActionFinishDate.setOnClickListener(mDeleteFinishDateAction);
        } else {
            mFinishDate.setText(null);
            mActionFinishDate.setText(R.string.dialog_service_order_finish_close);
            mActionFinishDate.setTextColor(getResources().getColor(R.color.color_primary));
            mActionFinishDate.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selector_white_button));
            mActionFinishDate.setOnClickListener(mEditFinishDateAction);
        }
    }

    private class EditFinishDateAction implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final Calendar c = Calendar.getInstance();
            new DatePickerDialog(getContext(), R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            c.set(year, month, day);
                            mOrderDetails.setFinishDateTime(c.getTime());
                            mOrderDetails.setUpdate(true);
                            updateFinishDateStatus();
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private class DeleteFinishDateAction implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mOrderDetails.setFinishDateTime(null);
            mOrderDetails.setUpdate(true);
            updateFinishDateStatus();
        }
    }
}