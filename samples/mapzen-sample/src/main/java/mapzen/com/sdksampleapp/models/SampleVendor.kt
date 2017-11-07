package mapzen.com.sdksampleapp.models

/**
 * Interface for returning lists of [Sample]s
 */
interface SampleVendor {
  fun samplesForNavId(navId: Int): Array<Sample>?
}