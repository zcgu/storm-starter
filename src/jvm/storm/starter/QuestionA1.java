/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package storm.starter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import storm.starter.bolt.Q1_PrinterBolt;
import storm.starter.spout.Q1_TwitterSpout;

public class QuestionA1 {
    public static void main(String[] args) {
       /* String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];
        String[] arguments = args.clone();
        String[] keyWords = Arrays.copyOfRange(arguments, 4, arguments.length);*/

        String consumerKey = "nhBnEhKUFnEF4i8KNOPGlurLA";
        String consumerSecret = "VlcAxGCKJVOgPTs7OyVAaMeFAMezoOQ0JZTtPvsrWF1jbNp9db";
        String accessToken = "3435593417-DlXLbVSCwpUWQEKDbqT7dlHb7iNoO8clPxwnZHe";
        String accessTokenSecret = "bldWCCmhFF2idcZJKZuZVHJ5gpoeywVhu2WRs86A8UCx2";
        String[] keyWords = {"winter","halloween","life",
                "apple","google","iphone","tech",
                "music","movie","star",
                "sports","nba","football","espn",
                "education","college","school",
                "politics","obama","vote","law",
                "travel","tourism","tour",
                "food","restaurant","cook","health",
                "arts","theater","literature",
                "business","economy","stock"
        };
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("twitter", new Q1_TwitterSpout(consumerKey, consumerSecret,
                                accessToken, accessTokenSecret, keyWords));
        builder.setBolt("print", new Q1_PrinterBolt())
                .shuffleGrouping("twitter");
                
                
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        //Utils.sleep(1000000);
        //cluster.shutdown();
    }
}
