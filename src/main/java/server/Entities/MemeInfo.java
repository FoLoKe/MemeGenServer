package server.Entities;

public class MemeInfo {
    private Meme meme;

    private int likes;
    private int dislikes;

    private boolean likeState;
    private boolean dislikeState;

    public void setMeme(Meme meme)
    {
        this.meme = meme;
    }

    public Meme getMeme()
    {
        return meme;
    }

    public void setLikes(int likes)
    {
        this.likes = likes;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setDislikes(int dislikes)
    {
        this.dislikes = dislikes;
    }

    public int getDislikes()
    {
        return dislikes;
    }

    public void setLikeState(boolean likeState)
    {
        this.likeState = likeState;
    }

    public boolean isLikeState()
    {
        return likeState;
    }

    public void setDislikeState(boolean dislikeState)
    {
        this.dislikeState = dislikeState;
    }

    public boolean isDislikeState()
    {
        return dislikeState;
    }
}
