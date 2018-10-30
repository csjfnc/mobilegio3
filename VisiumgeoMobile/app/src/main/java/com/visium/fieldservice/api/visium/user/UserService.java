package com.visium.fieldservice.api.visium.user;

import com.visium.fieldservice.api.visium.bean.request.LocalUsuarioRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LocalUsuarioResponse;
import com.visium.fieldservice.api.visium.bean.response.LoginResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * @author Andrew Willard
 */
interface UserService {

    @POST( "/auth/login")
    public void auth(Callback<GenericResponse<LoginResponse>> callback);

    @POST( "/LocalUsuario/LocalAtual")
    public void LocaUsuario(@Body LocalUsuarioRequest request, Callback<GenericResponse<LocalUsuarioResponse>> callback);

    @POST( "/LocalUsuario/Panico")
    public void LocaUsuarioPanico(@Body LocalUsuarioRequest request, Callback<GenericResponse<LocalUsuarioResponse>> callback);
}
