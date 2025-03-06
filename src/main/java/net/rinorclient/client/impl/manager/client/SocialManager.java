package net.rinorclient.client.impl.manager.client;

import net.minecraft.text.Text;
import net.rinorclient.client.api.social.SocialRelation;
import net.rinorclient.client.util.Globals;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SocialManager implements Globals {
    private final ConcurrentMap<String, SocialRelation> relationships = new ConcurrentHashMap<>();

    public boolean isRelation(String name, SocialRelation relation) {
        return relationships.get(name) == relation;
    }

    public boolean isFriend(String name) {
        return isRelation(name, SocialRelation.FRIEND);
    }

    public boolean isFriend(Text name) {
        return isRelation(name.getString(), SocialRelation.FRIEND);
    }

    public void addRelation(String name, SocialRelation relation) {
        if (mc.player != null && name.equals(mc.player.getDisplayName().getString())) {
            return;
        }
        final SocialRelation relationship = relationships.get(name);
        if (relationship != null) {
            relationships.replace(name, relation);
            return;
        }
        relationships.put(name, relation);
    }

    public void addFriend(String name) {
        addRelation(name, SocialRelation.FRIEND);
    }

    public void addFriend(Text name) {
        addRelation(name.getString(), SocialRelation.FRIEND);
    }

    public SocialRelation remove(String playerName) {
        return relationships.remove(playerName);
    }

    public SocialRelation remove(Text playerName) {
        return relationships.remove(playerName.getString());
    }

    public Collection<String> getRelations(SocialRelation relation) {
        final List<String> friends = new ArrayList<>();
        for (Map.Entry<String, SocialRelation> relationship :
                relationships.entrySet()) {
            if (relationship.getValue() == relation) {
                friends.add(relationship.getKey());
            }
        }
        return friends;
    }

    public Collection<String> getFriends() {
        return getRelations(SocialRelation.FRIEND);
    }
}
