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
package com.tailorfit.android.tailorfitapp.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateCustomerRequest(
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("phone_number")
    var phoneNumber: String? = null,
    @SerializedName("user_id")
    var userId: String? = null
) : Parcelable
