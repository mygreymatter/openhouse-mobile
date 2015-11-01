package com.mayo.openhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mayo.openhouse.datamodel.Comment;
import com.mayo.openhouse.datamodel.Property;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ActMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        /*ParseObject parseObject = new ParseObject("comment");
        parseObject.put("comment_text", "kanchana");
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null)
             Log.i(Tag.LOG,"Done:  "+e.getMessage());
            }
        });*/

        /*ParseQuery<User> query = ParseQuery.getQuery(User.class);
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> users, ParseException e) {
                Log.i(Tag.LOG,"Users: "+users);
                if(users != null){
                    OpenHouse.getInstance().mUsers = users;
                    for(User user: OpenHouse.getInstance().mUsers)
                        Log.i(Tag.LOG,user.getName());
                }
            }
        });*/

        findUsers();
        findComments();
        findProperty();

        /*Comment comment = new Comment();
        comment.put("comment_text", "hee");
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.i(Tag.LOG, "e:  " + e);
                if(e != null)
                    Log.i(Tag.LOG, "Done:  " + e.getMessage());
            }
        });*/
    }

    private void findUsers(){
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                Log.i(Tag.LOG,"Users: "+users);
                if(users != null){
                    for(ParseUser user: users)
                        Log.i(Tag.LOG, "" + user.getString("name"));
                }
            }
        });

    }

    private void findComments(){
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comm, ParseException e) {
                Log.i(Tag.LOG,"Users: "+comm);
                if(comm != null){
                    for(Comment commentText: comm)
                        Log.i(Tag.LOG, "" + commentText.getString("comment_text"));
                }
            }
        });
    }

    private void findProperty(){
        ParseQuery<Property> query = ParseQuery.getQuery(Property.class);
        query.findInBackground(new FindCallback<Property>() {
            @Override
            public void done(List<Property> properties, ParseException e) {
                Log.i(Tag.LOG,"Users: "+properties);
                if(properties != null){
                    for(Property property: properties)
                        Log.i(Tag.LOG, "" + property.getString("title"));
                }
            }
        });
    }
}
