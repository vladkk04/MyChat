package com.arkul.mychat.ui.adapters.recyclerViews

import java.util.UUID

open class BaseModel (
    open val id: String = UUID.randomUUID().toString()
)