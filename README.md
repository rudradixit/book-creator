# DISCLAIMER

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
the specific language governing permissions and limitations under the License.

# LICENSE

All of the source code for this project is ruled under the GNU General Public License, version 3.
Please refer to http://www.gnu.org/licenses/gpl-3.0.txt for additional information and legal text.

# OBJECTIVE

This code is part of my Master dissertation for MOSS/ISCTE/Portugal. Please contact me at rudradixit at gmail dot com.

# NOTES

This projects consists creating PDFs based on a series of links. Each input consists of a text file with a list of
links, one per line. A PDF file should be created based on each text file.

To run this code, one needs to create an input folder with textual files (let's call it "/tmp/hadoop-input/bc").
Let's also suppose that the intended output folder is "/tmp/hadoop-output/bc" (don't create this folder, Hadoop will do
it automatically). You can also provide a hadoop conf file. If you don't, the application will use the default one.

First, take a look at the build.properties file and customize it to your needs.

Then, run either one of the following commands. The first one runs the application directly from the build folder:

```
export HADOOP_CLASSPATH=/path/described/as/hadoop-output
hadoop org.rad.bc.core.BookCreatorRunner -conf conf/hadoop-local.xml /tmp/hadoop-input/bc /tmp/hadoop-output/bc
```

If you prefer running the JAR file, first use Ant to build one (it will append the current timestamp to the JAR name).
A JAR file will be placed in your dist folder. Then, run the following command:

```
hadoop jar book-creator.jar -conf conf/hadoop-local.xml /tmp/hadoop-input/bc /tmp/hadoop-output/bc
```