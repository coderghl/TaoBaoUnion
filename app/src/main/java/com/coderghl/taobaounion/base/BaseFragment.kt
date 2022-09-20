package com.coderghl.taobaounion.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        javaClass.genericSuperclass.also { type ->
            if (type is ParameterizedType) {
                val clazz = type.actualTypeArguments[0] as Class<VB>
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                _binding = method.invoke(null, layoutInflater) as VB
            }
        }
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    abstract fun onViewCreated()
}