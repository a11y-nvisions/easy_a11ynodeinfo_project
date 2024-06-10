package com.example.easy_a11ynodeinfo
import android.appwidget.AppWidgetManager
import android.os.Build
import android.view.View
import android.view.ViewParent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import android.widget.Switch
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat
import com.google.android.material.tabs.TabLayout.Tab
import java.util.Objects
import kotlin.reflect.KClass
import com.google.android.material.R as MaterialRes

enum class AccessibilityRole(val value: CharSequence) {
    BUTTON(Button::class.java.name),
    SWITCH(Switch::class.java.name),
    TAB(Tab::class.java.name),
    LIST("AccessibilityRole.semanticList"),
    CHECKBOX(CheckBox::class.java.name),
    RADIO_BUTTON(RadioButton::class.java.name),
    TOGGLE_BUTTON(ToggleButton::class.java.name)
}

data class EasyA11yNodeInfoInit (
    var checkable:Boolean? = null,
    var checked:Boolean? = null,
    var clickable:Boolean? = null,
    var itemCount:Int? = 0,
    var itemIndex:Int? = 0,
    var heading:Boolean? = null,
    var enabled:Boolean? = null,
    var selected:Boolean? = null,
    var expanded:Boolean? = null,
    var focusable:Boolean? = null,
    var label:String? = null,
    var role: AccessibilityRole? = null,
    var hint:String? = null,
    var importance:AccessibilityImportance? = null
)

enum class AccessibilityImportance(val value:Int){
    SHOW_ALL_HIREACHIES (View.IMPORTANT_FOR_ACCESSIBILITY_YES),
    HIDE_THIS_HIREACHY_ONLY (View.IMPORTANT_FOR_ACCESSIBILITY_NO),
    HIDE_ALL_HIREACHIES (View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS),
    AUTO(View.IMPORTANT_FOR_ACCESSIBILITY_AUTO)
}

class EasyA11yNodeInfoManager(val view: View) {
    private var semantics: EasyA11yNodeInfoInit = EasyA11yNodeInfoInit()
    private var stateDesc_switchCheckedTrue = view.context.getString(MaterialRes.string.abc_capital_on)
    private var stateDesc_switchCheckedFalse = view.context.getString(MaterialRes.string.abc_capital_off)

    fun setViewAccessible(accessible: AccessibilityImportance?):EasyA11yNodeInfoManager{
        this.isAccessible = accessible
        this.reloadNodeInfo()
        return this
    }
    private var isAccessible:AccessibilityImportance?
        get( ) = this.semantics.importance
        set(value:AccessibilityImportance?) { this.semantics.importance = value  }

    fun setFocusable(focusable:Boolean):EasyA11yNodeInfoManager {
        this.isFocusable = focusable
        this.reloadNodeInfo()
        return this
    }

    private var isFocusable:Boolean?
        get() = this.semantics.focusable
        set(v) { this.semantics.focusable = v }
    fun setClickable(clickable:Boolean):EasyA11yNodeInfoManager {
        this.semantics.clickable = clickable
        this.reloadNodeInfo()
        return this
    }

    private var isClickable:Boolean?
        get() = this.semantics.clickable
        set(v) { this.semantics.clickable = v }


    fun enabled(enabled:Boolean): EasyA11yNodeInfoManager {
        this.isEnabled = enabled
        this.reloadNodeInfo()
        return this
    }
    private var isEnabled:Boolean?
        get() = this.semantics.enabled
        set(v) { this.semantics.enabled = v }

    fun setItemIndex(num: Int): EasyA11yNodeInfoManager {
        this.itemIndex = num
        this.reloadNodeInfo()
        return this
    }
    private var itemIndex:Int?
        get() = this.semantics.itemIndex
        set(num) { this.semantics.itemIndex = num }


    fun setItemCount(num: Int): EasyA11yNodeInfoManager {
        this.itemCount = num
        this.reloadNodeInfo()
        return this
    }
    private var itemCount:Int?
        get() = this.semantics.itemCount
        set(num) { this.semantics.itemCount = num }

    fun setHeading(boolean: Boolean): EasyA11yNodeInfoManager {
        this.isHeading = boolean
        this.reloadNodeInfo()
        return this
    }
    private var isHeading:Boolean?
        get() = this.semantics.heading
        set(v) { this.semantics.heading = v }

    fun setSelected(boolean: Boolean): EasyA11yNodeInfoManager {
        this.isSelected = boolean
        this.reloadNodeInfo()
        return this
    }
    private var isSelected:Boolean?
        get() = this.semantics.selected
        set(v) { this.semantics.selected = v }

    fun setCheckable(boolean:Boolean): EasyA11yNodeInfoManager {
        this.isCheckable = boolean
        this.reloadNodeInfo()
        return this
    }
    private var isCheckable:Boolean?
        get() = this.semantics.checkable
        set(v) {  this.semantics.checkable = v }

    fun setChecked(boolean:Boolean): EasyA11yNodeInfoManager {
        this.isChecked = boolean

        this.reloadNodeInfo()
        return this
    }
    private var isChecked:Boolean?
        get() = this.semantics.checked
        set(v) {  this.semantics.checked = v}

    fun setExpanded(boolean:Boolean): EasyA11yNodeInfoManager {
        this.isExpanded = boolean
        this.reloadNodeInfo()
        return this
    }
    private var isExpanded:Boolean?
        get() = this.semantics.expanded
        set(v) {  this.semantics.expanded = v}

    fun setLabel(string:String): EasyA11yNodeInfoManager {
        this.label = string
        this.reloadNodeInfo()
        return this
    }
    private var label:String?
        get() = this.semantics.label
        set(v) {  this.semantics.label = v}
    fun setHint(string:String): EasyA11yNodeInfoManager {
        this.hint = string
        this.reloadNodeInfo()
        return this
    }
    private var hint:String?
        get() = this.semantics.hint
        set(v) {  this.semantics.hint = v }

    fun setRole(role: AccessibilityRole): EasyA11yNodeInfoManager {
        this.role = role
        this.reloadNodeInfo()
        return this
    }

    private var role: AccessibilityRole?
        get() = this.semantics.role
        set(v) {
            this.semantics.role = v
            this.reloadNodeInfo()
        }

    fun resetNodeInfo() {
        this.semantics = EasyA11yNodeInfoInit()
        this.reloadNodeInfo()
    }

    private fun reloadNodeInfo() {
        view.createAccessibilityNodeInfo()
    }

    init {
        this.view.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val infoCompat = AccessibilityNodeInfoCompat.wrap(info)
                val index = itemIndex
                val count = itemCount
                val selected = isSelected

                isAccessible?.let {
                    view.importantForAccessibility = it.value
                }

                if(role == AccessibilityRole.LIST && count != null) {
                    infoCompat.setCollectionInfo (
                        CollectionInfoCompat.obtain(
                            1,
                            count,
                            false,
                            CollectionInfo.SELECTION_MODE_SINGLE
                        )
                    )
                    isAccessible=AccessibilityImportance.SHOW_ALL_HIREACHIES
                }

                if(index != null) {
                    infoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(
                        0,
                        1,
                        index,
                        1,
                        false,
                        selected == true
                    ))
                }

                isFocusable?.let {
                    info.isFocusable = it
                }

                isClickable?.let {
                    info.isClickable = it
                }

                isEnabled?.let {
                    info.isEnabled = it
                }

                isCheckable?.let {
                    info.isCheckable = it
                }

                isChecked?.let {
                    info.isChecked =  it

                    if( isCheckable == null || isCheckable == false) {
                        isCheckable = true
                    }

                    if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.R ) {
                        if(role == AccessibilityRole.SWITCH || role == AccessibilityRole.TOGGLE_BUTTON) {

                            if ( view.context.resources.configuration.locales.get(0).language == "ko" ){
                                ViewCompat.setStateDescription(view,if (it) "켬" else "끔")
                            } else {
                                ViewCompat.setStateDescription(view,if (it) stateDesc_switchCheckedTrue else
                                    stateDesc_switchCheckedFalse)
                            }

                        } else {
                            ViewCompat.setStateDescription(view,null)
                        }
                    }
                }

                isSelected?.let {
                    info.isSelected =  it
                }

                isHeading?.let {
                    info.isHeading =  it
                }

                isExpanded?.let {
                    if(it) {
                        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE)
                    } else {
                        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND)
                    }
                }

                label?.let {
                    info.contentDescription = it
                }

                hint?.let {
                    info.hintText = it
                }

                role.let {
                    if(it != null) {
                        if (it.value == AccessibilityRole.TAB.value) {
                            if (selected == true) {
                                infoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK)
                                infoCompat.isClickable = false
                            }
                            infoCompat.roleDescription = view.resources.getString(MaterialRes.string.item_view_role_description)
                            infoCompat.isCheckable = false
                            infoCompat.isChecked = false
                        }
                    }
                }
            }
        }

        this.isAccessible = this.semantics.importance
        this.isChecked = this.semantics.checked
        this.isCheckable = this.semantics.checkable
        this.isEnabled = this.semantics.enabled
        this.isClickable = this.semantics.clickable
        this.isFocusable = this.semantics.focusable
        this.hint = this.semantics.hint
        this.label = this.semantics.label
        this.role = this.semantics.role
        this.isExpanded = this.semantics.expanded
        this.isSelected = this.semantics.selected
        reloadNodeInfo()
    }
}

val View.isNodeInfoInitialized:Boolean
    get() { return this.getTag(R.id.easy_a11y_node_info) == null }

val View.nodeInfo: EasyA11yNodeInfoManager
    get() {
        return if( !isNodeInfoInitialized ) {
            this.getTag(R.id.easy_a11y_node_info) as EasyA11yNodeInfoManager // return
        } else {
            val manager = EasyA11yNodeInfoManager(this)
            this.setTag(R.id.easy_a11y_node_info, manager)
            manager // return
        }
    }


