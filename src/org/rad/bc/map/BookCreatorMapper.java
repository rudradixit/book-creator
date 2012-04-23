/*
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * All of the source code for this project is ruled under the GNU General Public License, version 3.
 * Please refer to http://www.gnu.org/licenses/gpl-3.0.txt for additional information and legal text.
 *
 * This code is part of my Master dissertation for MOSS/ISCTE/Portugal.
 * Please contact me at rudradixit at gmail dot com.
 */

package org.rad.bc.map;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BookCreatorMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringBuilder output = download(value.toString());
        context.write(key, new Text(output.toString()));
    }

    private StringBuilder download(String link) {
        BufferedReader reader = null;
        InputStream stream = null;
        String line;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.getProperty("line.separator"));
            }
        } catch (Exception exc) {
            throw new RuntimeException("Error while downloading link [" + link + "].", exc);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }

                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ioexc) {
                ioexc.printStackTrace();
            }
        }

        return builder;
    }
}