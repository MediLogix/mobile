package hr.algebra.nasaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.nasaapp.adapter.ItemPagerAdapter
import hr.algebra.nasaapp.databinding.ActivityItemPagerBinding
import hr.algebra.nasaapp.framework.fetchItems
import hr.algebra.nasaapp.model.Item

const val POSITION = "hr.algebra.nasaapp.item_pos"

class ItemPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemPagerBinding

    private lateinit var items: MutableList<Item>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        items = fetchItems()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = ItemPagerAdapter(this, items)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}