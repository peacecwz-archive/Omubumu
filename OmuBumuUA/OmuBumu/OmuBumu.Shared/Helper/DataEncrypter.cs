using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Text;

namespace OmuBumu.Helper
{
    public class DataEncrypter
    {
        private static string Original = "ABCDEFGHIİJKLMNOPRSŞTUÜVYZWXQ1234567890!'^+%&/()=?_><:.;,~>£#½{[]}|abcdefghıijklmnoöprsştuüvyzwqx";

        private static string Sezar = "W>niE?IPK#RQxlNs(Br£:9J/w>50m]FVdvh2T}<M6İ3HşeXU_;ÜfG~Z{jyüpöCqzc)ıAaD1ŞYb.½SoLg8%7O&k,4!u'+|=^[t";

        private static string Base64Encode(string plainText)
        {
            var plainTextBytes = System.Text.Encoding.UTF8.GetBytes(plainText);
            return System.Convert.ToBase64String(plainTextBytes);
        }

        private static string Base64Decode(string base64EncodedData)
        {
            var base64EncodedBytes = System.Convert.FromBase64String(base64EncodedData);
            return System.Text.Encoding.UTF8.GetString(base64EncodedBytes, 0, base64EncodedBytes.Length);
        }

        public static string Encrypt(string decoded)
        {
            decoded = Base64Encode(decoded);
            string encoded = "";
            foreach (var ch in decoded)
            {
                encoded += Sezar[Original.IndexOf(ch)].ToString();
            }
            return (decoded);
        }

        public static string Decrypt(string encoded)
        {
            string decoded = "";
            foreach (var ch in encoded)
            {
                decoded += Original[Sezar.IndexOf(ch)].ToString();
            }
            return (Base64Decode(encoded));
        }

        public static string CompressString(string text)
        {
            byte[] buffer = Encoding.UTF8.GetBytes(text);
            var memoryStream = new MemoryStream();
            using (var gZipStream = new GZipStream(memoryStream, CompressionMode.Compress, true))
            {
                gZipStream.Write(buffer, 0, buffer.Length);
            }

            memoryStream.Position = 0;

            var compressedData = new byte[memoryStream.Length];
            memoryStream.Read(compressedData, 0, compressedData.Length);

            var gZipBuffer = new byte[compressedData.Length + 4];

            System.Buffer.BlockCopy(compressedData, 0, gZipBuffer, 4, compressedData.Length);
            System.Buffer.BlockCopy(BitConverter.GetBytes(buffer.Length), 0, gZipBuffer, 0, 4);
            return Encoding.UTF8.GetString(gZipBuffer, 0, gZipBuffer.Length);
        }

        public static string DecompressString(string compressedText)
        {
            byte[] gZipBuffer = Convert.FromBase64String(compressedText);
            using (var memoryStream = new MemoryStream())
            {
                int dataLength = BitConverter.ToInt32(gZipBuffer, 0);
                memoryStream.Write(gZipBuffer, 4, gZipBuffer.Length - 4);

                var buffer = new byte[dataLength];

                memoryStream.Position = 0;
                using (var gZipStream = new GZipStream(memoryStream, CompressionMode.Decompress))
                {
                    gZipStream.Read(buffer, 0, buffer.Length);
                }

                return Encoding.UTF8.GetString(buffer, 0, buffer.Length);
            }
        }
    }
}
