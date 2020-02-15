package classloader

import java.io.*

class FileSystemClassLoader(private val rootDir: String) : ClassLoader() {
    @Throws(ClassNotFoundException::class)
    override fun findClass(name: String): Class<*> {
        val classData = getClassData(name)
        return if (classData == null) {
            throw ClassNotFoundException()
        } else {
            defineClass(name, classData, 0, classData.size)
        }
    }

    /**
     * 服务器加密数据返回
     * @param className
     * @return
     */
    private fun getClassData(className: String): ByteArray? {
        val path = classNameToPath(className)
        try {
            val ins: InputStream = FileInputStream(path)
            val baos = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var bytesNumRead = 0
            while (ins.read(buffer).also { bytesNumRead = it } != -1) {
                baos.write(buffer, 0, bytesNumRead)
            }
            ins.close()
            return baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun classNameToPath(className: String): String {
        return (rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class")
    }

}