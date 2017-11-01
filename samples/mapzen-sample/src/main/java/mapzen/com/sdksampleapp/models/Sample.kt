package mapzen.com.sdksampleapp.models

import mapzen.com.sdksampleapp.fragments.BaseFragment
import kotlin.reflect.KClass

/**
 * Represents a feature of the SDK to demonstrate from within a navigation tab.
 */
data class Sample(val title: String, val fragmentClass: KClass<out BaseFragment>)
