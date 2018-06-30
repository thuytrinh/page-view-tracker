package thuy.pageviewtracker

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import thuy.pageviewtracker.databinding.ItemBinding
import thuy.pageviewtracker.databinding.MainBinding

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

    val binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.main)
    binding.setLifecycleOwner(this)
    binding.viewModel = viewModel

    binding.viewPager.adapter = object : PagerAdapter() {
      override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
      }

      override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = viewModel.items[position]
        return ItemBinding.inflate(layoutInflater, container, true).also {
          it.textView.text = item
        }.root
      }

      override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
      }

      override fun getCount(): Int = viewModel.items.size
    }
    binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
      override fun onPageSelected(position: Int) {
        viewModel.dispatchOnPageSelected(position)
      }
    })
    viewModel.dispatchOnInitialPageSelected()
  }
}
