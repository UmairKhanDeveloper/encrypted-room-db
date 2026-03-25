object EncryptionHelper {

    private const val KEY = "1234567890123456"

    fun encrypt(text: String): String {
        if (text.isEmpty()) return text

        val cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding")
        val key = javax.crypto.spec.SecretKeySpec(KEY.toByteArray(), "AES")
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key)

        return android.util.Base64.encodeToString(
            cipher.doFinal(text.toByteArray()),
            android.util.Base64.NO_WRAP
        )
    }

    fun decrypt(encrypted: String): String {
        if (encrypted.isEmpty()) return encrypted

        return try {
            val cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding")
            val key = javax.crypto.spec.SecretKeySpec(KEY.toByteArray(), "AES")
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key)

            String(
                cipher.doFinal(
                    android.util.Base64.decode(encrypted, android.util.Base64.NO_WRAP)
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }
    }
}