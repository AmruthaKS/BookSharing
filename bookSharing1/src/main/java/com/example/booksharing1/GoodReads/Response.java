package com.example.booksharing1.GoodReads;
public class Response
{

    private User _User;

    public void clear()
    {
        this.get_User().clear();
    }

    public void copy() {
        Response responseCopy = new Response();
        responseCopy.set_User(this.get_User().copy());
    }

    public User get_User()
    {
        return _User;
    }

    public void set_User(User user)
    {
        _User = user;
    }


}