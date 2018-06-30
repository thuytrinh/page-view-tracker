package thuy.pageviewtracker

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
  private val tag = javaClass.simpleName
  val items by lazy { (0..10).map { it.toString() } }
  private val onPageSelected = PublishSubject.create<Int>()

  init {
    onPageSelected
        .observeOn(Schedulers.computation())
        .debounce(3, TimeUnit.SECONDS)
        .map { "Tracked page $it" }
        .subscribe { Log.d(tag, it) }
  }

  fun dispatchOnInitialPageSelected() {
    onPageSelected.onNext(0)
  }

  fun dispatchOnPageSelected(position: Int) {
    onPageSelected.onNext(position)
  }
}
