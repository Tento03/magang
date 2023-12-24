package com.example.suitmedia.api

data class UserResponse( val page: Int,
                         val per_page: Int,
                         val total: Int,
                         val total_pages: Int,
                         var data:List<User>) {
}