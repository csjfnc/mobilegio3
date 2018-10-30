package com.visium.fieldservice.api.visium.post;

import com.visium.fieldservice.api.visium.bean.request.PostCreateRequest;
import com.visium.fieldservice.api.visium.bean.request.PostDeleteRequest;
import com.visium.fieldservice.api.visium.bean.request.PostUpdateRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PostResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import java.util.List;

/**
 * @author Andrew Willard
 */
interface PostService {

    @GET( "/poste/getbyos")
    public void getPosts(
            @Query("idordemdeservico") long id,
            Callback<GenericResponse<List<PostResponse>>> callback);

    @PUT( "/poste/edit")
    public void save(@Body PostUpdateRequest request,
                     Callback<GenericResponse<PostResponse>> callback);

    @POST( "/poste/add")
    public void create(@Body PostCreateRequest request,
                     Callback<GenericResponse<PostResponse>> callback);

    @POST( "/poste/deletar")
    public void delete(@Body PostDeleteRequest request,
                       Callback<GenericResponse> callback);
}