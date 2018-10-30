package com.visium.fieldservice.api.visium.post;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.PostCreateRequest;
import com.visium.fieldservice.api.visium.bean.request.PostDeleteRequest;
import com.visium.fieldservice.api.visium.bean.request.PostUpdateRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PostResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
@SuppressWarnings("unchecked")
public class PostApi extends FieldServiceApi<PostService> {

    private static final PostApi INSTANCE = new PostApi();

    protected PostApi() {
        super(PostService.class);
    }

    public static void getPosts(long orderId, final VisiumApiCallback<GenericResponse<List<PostResponse>>> callback) {

        try {

            INSTANCE.service.getPosts(orderId, new DefaultCallback<GenericResponse<List<PostResponse>>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PostApi.class, e);
        }
    }

    public static void save(PostUpdateRequest request, final VisiumApiCallback<GenericResponse<PostResponse>> callback) {

        try {

            INSTANCE.service.save(request, new DefaultCallback<GenericResponse<PostResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PostApi.class, e);
        }
    }

    public static void create(PostCreateRequest request, final VisiumApiCallback<GenericResponse<PostResponse>> callback) {

        try {

            INSTANCE.service.create(request, new DefaultCallback<GenericResponse<PostResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PostApi.class, e);
        }
    }

    public static void delete(PostDeleteRequest request, final VisiumApiCallback<GenericResponse<PostResponse>> callback) {

        try {

            INSTANCE.service.delete(request, new DefaultCallback<GenericResponse<PostResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PostApi.class, e);
        }
    }
}
