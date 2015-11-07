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
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import storm.starter.bolt.*;
import storm.starter.spout.Q2_HashtagSpout;
import storm.starter.spout.Q2_NumbersSpout;
import storm.starter.spout.Q2_TwitterSpout;

import java.util.HashMap;
import java.util.Map;


public class QuestionA2 {


    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {

        String consumerKey = "PAENc9WI0klCBwLPhyOqfVNMt";
        String consumerSecret = "9BXbYQbXSjCRgMzU6UhgopMZe3aBVw7bjTS16fSMyTWef3QPyK";
        String accessToken = "3435593417-8EQvMbiAVpEQeZFR6uvJDk1c5Ow05ZudGbZrIpa";
        String accessTokenSecret = "4v2pFJykOjDfA14aGLKPMCHRSbyx0zXGGP9mygPJDM3I9";

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

        String hashtags_origin = "fit music rap outtie200 streamys opportunity google deals stavrosvelissaris business food oppression tasneemchopra bondage startup tcot weightloss jersey football home androidgames college career education repost nba hiphop productdesign obama iphone6 forcefordaniel top cookbook notoxichorsemeat aliakhan health singapore amas smallbiz solution law yelp amazon nfl saintforhumanity kickstarter recipe aptask nowplaying sports pakistani auzibiz poverty gameinsight indiegogo ukvoty1d iphonegames fatloss entrepreneur 2a religion marketing kygov aldubnewcharacter ruhalalam tech proshot psylinlang android video apple news thoughtleaders electionday program twitter worldseries case work iphone cars pjnet prankvsprank foodporn banup travel halloween ucl agentsofshield myfitnesspal politics videomarketing dabemoji moneybagz madeintheam soccer affiliate " +
                "music trip google freekeshanow stavrosvelissaris mufc seo business stoprush tcot gangstalking fl india football bitcoin androidgames education nba hiphop save soundcloud top cookbook notoxichorsemeat 520promo retweet health amas crowdfunding 7thweekmsg2 starwars contest rnb yelp mentalhealth nfl kickstarter p2 nowplaying sports nature dogecoin gameinsight exo dance indiegogo photography art tour kygov fashion aldubnewcharacter florida socialmedia rhianrockssundownbeatssg passion tech psylinlang showtimethetask android life news technology money games worldseries uniteblue iphone cars ipadgames makemytrip oomf androi travel halloween ipad politics dabemoji gorillaglass4sweeps landscape " +
                "music stavrosvelissaris innovation business jobs food workout topps fitness tcot journey selfhe cardinals jersey football education mplusrewards illshowyou nba pcas top cookbook vacation share notoxichorsemeat health positive getfit amas copolitics solution me law nfl ebay kickstarter hiring best boy sports instagram cfbplayoff cover bigbang gameinsight singing indiegogo clemson photography iphonegames colts art aldubnewcharacter socialmedia acapella tech psylinlang ?? pushawardskathniels android video apple cfp25 news technology electionday espn positivity worldseries iphone ipadgames teacherfriends card artist ravens travel nytimes ipad job winter dabemoji voy?ge michaelbuble gorillaglass4sweeps win ";
        String[] hashtags = hashtags_origin.split(" ");
        for (int i=0;i<hashtags.length;i++)
            for (int j=i+1;j<hashtags.length;j++)
                if(hashtags[i].equals(hashtags[j])) hashtags[j]="";

        int OutputFrequency = 150;
        String fname1="QuestionA2_tweetdata_1";
        String fname2="QuestionA2_countdata_1";


        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("twitter", new Q2_TwitterSpout(consumerKey, consumerSecret,accessToken, accessTokenSecret, keyWords));
        builder.setSpout("hashtags", new Q2_HashtagSpout(hashtags));
        builder.setSpout("number", new Q2_NumbersSpout());

        builder.setBolt("change", new Q2_ChangeBolt(OutputFrequency))
                .globalGrouping("hashtags")
                .globalGrouping("number");


        builder.setBolt("select", new Q2_SelectBolt(fname1))
                .allGrouping("change")
                .shuffleGrouping("twitter");
        builder.setBolt("split", new Q2_SplitBolt(), 4)
                .shuffleGrouping("select");
        builder.setBolt("count", new Q2_CountBolt(OutputFrequency),4)
                .fieldsGrouping("split", new Fields("word"));
        builder.setBolt("print", new Q2_PrinterBolt(OutputFrequency,fname2))
                .globalGrouping("count");


 /*       builder.setBolt("helper", new Q2_SelectBolt_helper_selecthashtag())
                .shuffleGrouping("twitter");
        builder.setBolt("count", new Q2_CountBolt(OutputFrequency),4)
                .fieldsGrouping("helper", new Fields("word"));
        builder.setBolt("print", new Q2_PrinterBolt(OutputFrequency,fname2))
                .globalGrouping("count");
*/



        Config conf = new Config();
        if (args != null && args.length > 0) {
            conf.setNumWorkers(4);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {
            conf.setMaxTaskParallelism(4);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", conf, builder.createTopology());
            Utils.sleep(1000000);
            cluster.shutdown();
        }
    }
}
