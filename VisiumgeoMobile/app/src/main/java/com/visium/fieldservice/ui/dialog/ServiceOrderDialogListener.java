package com.visium.fieldservice.ui.dialog;

import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface ServiceOrderDialogListener {

    void onServiceOrderSaveButtonClicked(ServiceOrderDetails orderDetails, List<Post> posts);
    void onServiceOrderPostsButtonClicked(ServiceOrderDetails orderDetails);
    void onServiceOrderPublishButtonClicked(ServiceOrderDetails orderDetails);

}
