package com.mlmvpn.scanner.utils

/**
 * Simplified Chinese for the app's two-language tr(fa, en) texts.
 *
 * Keyed by the English text of a tr() call: when the app language is Chinese, tr() looks
 * its English text up here and falls back to the English itself when a text has no
 * translation yet (texts containing $ templates are not in the map, on purpose: their
 * runtime form differs from call to call, so they cannot be keyed by their literal).
 */
object ZhTr {
    private val MAP: Map<String, String> by lazy {
        val m = HashMap<String, String>(1768)
        m.putAll(ZhTrData1.MAP)
        m.putAll(ZhTrData2.MAP)
        m.putAll(ZhTrData3.MAP)
        m.putAll(ZhTrData4.MAP)
        m.putAll(ZhTrData5.MAP)
        m.putAll(ZhTrData6.MAP)
        m
    }

    fun translate(en: String): String? {
        MAP[en]?.let { return it }
        // Texts built with $ interpolations arrive already interpolated, so they can
        // never hit the literal table above; match them against the template skeletons
        // in ZhTrTemplates (most specific first) and fill the Chinese template with
        // the captured values.
        for (t in ZH_TEMPLATES) {
            val caps = t.match(en) ?: continue
            return t.render(caps)
        }
        return null
    }
}
