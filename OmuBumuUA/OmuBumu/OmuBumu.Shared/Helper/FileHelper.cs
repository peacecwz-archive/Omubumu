using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Text;
using System.Threading.Tasks;
using Windows.Graphics.Imaging;
using Windows.Storage;
using Windows.Storage.Compression;
using Windows.Storage.Streams;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;

namespace OmuBumu.Helper
{
    public class FileHelper
    {
        public static async Task<byte[]> ReadFile(StorageFile file)
        {
            byte[] fileBytes = null;
            using (IRandomAccessStreamWithContentType stream = await file.OpenReadAsync())
            {
                fileBytes = new byte[stream.Size];
                using (DataReader reader = new DataReader(stream))
                {
                    await reader.LoadAsync((uint)stream.Size);
                    reader.ReadBytes(fileBytes);
                }
            }

            return fileBytes;
        }

        public static async Task<Byte[]> GetDataFromUri(Uri uri)
        {
            using (System.Net.Http.HttpClient Client = new System.Net.Http.HttpClient())
            {
                Client.BaseAddress = uri;
                var resultContext = await Client.GetAsync(uri);
                if (resultContext != null)
                {
                    return await resultContext.Content.ReadAsByteArrayAsync();
                }
            }
            return null;
        }

        public static async Task<BitmapImage> ByteToImage(byte[] imageData)
        {
            try
            {
                using (InMemoryRandomAccessStream ms = new InMemoryRandomAccessStream())
                {
                    using (DataWriter writer = new DataWriter(ms.GetOutputStreamAt(0)))
                    {
                        writer.WriteBytes(imageData);
                        await writer.StoreAsync();
                    }

                    var image = new BitmapImage();
                    await image.SetSourceAsync(ms);
                    return image;
                }
            }
            catch
            {
                return new BitmapImage();
            }
        }

        public static async Task<BitmapImage> MergeImages(int singleWidth, int singleHeight, params byte[][] pixelData)
        {
            int perRow = (int)Math.Ceiling(Math.Sqrt(pixelData.Length));
            byte[] mergedImageBytes = new byte[singleHeight * singleWidth * perRow * perRow * 4];
            for (int i = 0; i < pixelData.Length; i++)
            {
                LoadPixelBytesAt(ref mergedImageBytes, pixelData[i], (i % perRow) * singleWidth, (i / perRow) * singleHeight, perRow * singleWidth, singleWidth, singleHeight);
            }
            InMemoryRandomAccessStream mem = new InMemoryRandomAccessStream();
            var encoder = await BitmapEncoder.CreateAsync(BitmapEncoder.BmpEncoderId, mem);
            encoder.SetPixelData(BitmapPixelFormat.Bgra8, BitmapAlphaMode.Ignore, (uint)(singleHeight * perRow), (uint)(singleWidth * perRow), 91, 91, mergedImageBytes);
            await encoder.FlushAsync();
            BitmapImage bmp = new BitmapImage();
            await bmp.SetSourceAsync(mem);
            return bmp;
        }

        private static void LoadPixelBytesAt(ref byte[] dest, byte[] src, int destX, int destY, int destW, int srcW, int srcH)
        {
            for (int i = 0; i < srcH; i++)
            {
                for (int j = 0; j < srcW; j++)
                {
                    if (src.Length < ((i * srcW + j + 1) * 4)) return;
                    for (int p = 0; p < 4; p++)
                        dest[((destY + i) * destW + destX + j) * 4 + p] = src[(i * srcW + j) * 4 + p];
                }
            }
        }

        public async static Task<byte[]> ReadFielToByteArray(StorageFile file)
        {
            byte[] fileBytes = null;
            using (IRandomAccessStreamWithContentType stream = await file.OpenReadAsync())
            {
                fileBytes = new byte[stream.Size];
                using (DataReader reader = new DataReader(stream))
                {
                    await reader.LoadAsync((uint)stream.Size);
                    reader.ReadBytes(fileBytes);
                }
            }
            return fileBytes;
        }
        
    }
}
