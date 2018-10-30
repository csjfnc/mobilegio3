package com.visium.fieldservice.controller;

import android.content.Context;

import com.visium.fieldservice.api.visium.bean.request.PostCreateRequest;
import com.visium.fieldservice.api.visium.bean.request.PostDeleteRequest;
import com.visium.fieldservice.api.visium.bean.request.PostUpdateRequest;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PostResponse;
import com.visium.fieldservice.api.visium.post.PostApi;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrew Willard
 */
public class PostController {

    private static final PostController INSTANCE = new PostController();

    private PostController() {}

    public static PostController get() {
        return INSTANCE;
    }

    public void getPosts(long orderId, final VisiumApiCallback<List<Post>> callback) {

        PostApi.getPosts(orderId, new VisiumApiCallback<GenericResponse<List<PostResponse>>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<List<PostResponse>> response, ErrorResponse e) {

                List<Post> posts;

                if (e == null && response != null && response.getData() != null) {
                    posts = new ArrayList<>(response.getData().size());
                    for (PostResponse resp : response.getData()) {
                        posts.add(new Post(resp));
                    }
                } else {
                    posts = Collections.<Post>emptyList();
                }

                callback.callback(posts, e);
            }
        });
    }

    public void save(Context ctx, Post post, final VisiumApiCallback<Post> callback) {
        PostApi.save(new PostUpdateRequest(post), new VisiumApiCallback<GenericResponse<PostResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PostResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Post(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void create(Context ctx, Post post, final VisiumApiCallback<Post> callback) {
        PostApi.create(new PostCreateRequest(post), new VisiumApiCallback<GenericResponse<PostResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PostResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Post(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void delete(Context ctx, Post post, final VisiumApiCallback<Post> callback) {
        PostApi.delete(new PostDeleteRequest(post), new VisiumApiCallback<GenericResponse<PostResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PostResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Post(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

}