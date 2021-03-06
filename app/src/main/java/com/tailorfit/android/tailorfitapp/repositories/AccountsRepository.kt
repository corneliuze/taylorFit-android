/**
 * Copyright (c) 2020 Falaye Iyanuoluwa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tailorfit.android.tailorfitapp.repositories

import com.tailorfit.android.networkutils.Result
import com.tailorfit.android.networkutils.toResult
import com.tailorfit.android.tailorfitapp.apis.TailorFitApIService
import com.tailorfit.android.tailorfitapp.models.request.SignInRequest
import com.tailorfit.android.tailorfitapp.models.request.SignUpRequest
import com.tailorfit.android.tailorfitapp.models.response.SignUpResponse
import io.reactivex.Single
import javax.inject.Inject

class AccountsRepository @Inject constructor(private val tailorFitApIService: TailorFitApIService) {
    fun signUp(body: SignUpRequest): Single<Result<SignUpResponse>> {
        return tailorFitApIService.signUp(body).toResult()
    }

    fun signIn(body: SignInRequest): Single<Result<SignUpResponse>> {
        return tailorFitApIService.signIn(body).toResult()
    }
}
