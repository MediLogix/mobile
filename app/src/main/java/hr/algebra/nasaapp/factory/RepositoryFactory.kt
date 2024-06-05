package hr.algebra.nasaapp.factory

import android.content.Context
import hr.algebra.nasaapp.dao.NasaSqlHelper

fun getNasaRepository(context: Context?) = NasaSqlHelper(context)