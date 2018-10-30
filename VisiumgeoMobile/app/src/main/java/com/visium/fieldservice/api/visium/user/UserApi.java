package com.visium.fieldservice.api.visium.user;

import android.util.Log;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.LocalUsuarioRequest;
import com.visium.fieldservice.api.visium.bean.request.LoginRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LocalUsuarioResponse;
import com.visium.fieldservice.api.visium.bean.response.LoginResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.LocalUsuario;
import com.visium.fieldservice.util.LogUtils;

import retrofit.RequestInterceptor;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
@SuppressWarnings("unchecked")
public class UserApi extends FieldServiceApi<UserService> {

    private static final UserApi INSTANCE = new UserApi();

    protected UserApi() {
        super(UserService.class);
    }

    public static void auth(final LoginRequest loginRequest, final VisiumApiCallback<GenericResponse<LoginResponse>> callback) {
        try {
            UserService userService = INSTANCE.getNewRestBuilder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", loginRequest.getAuthorization());
                            Log.e("Auto",loginRequest.getAuthorization());
                            INSTANCE.getNewRequestInterceptor().intercept(request);
                        }
                    }).build().create(UserService.class);

            userService.auth(new DefaultCallback<GenericResponse<LoginResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(UserApi.class, e);
        }
    }

    public static void LocalUserAtual(LocalUsuarioRequest request, final VisiumApiCallback<GenericResponse<LocalUsuarioResponse>> callback) {
        UserService userService = INSTANCE.getNewRestBuilder().setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
            }
        }).build().create(UserService.class);

        userService.LocaUsuario(request, new DefaultCallback<GenericResponse<LocalUsuarioResponse>>(callback));
    }

    public static void LocalUserAtualPanico(LocalUsuarioRequest request, final VisiumApiCallback<GenericResponse<LocalUsuarioResponse>> callback) {
        UserService userService = INSTANCE.getNewRestBuilder().setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
            }
        }).build().create(UserService.class);

        userService.LocaUsuarioPanico(request, new DefaultCallback<GenericResponse<LocalUsuarioResponse>>(callback));
    }
}
