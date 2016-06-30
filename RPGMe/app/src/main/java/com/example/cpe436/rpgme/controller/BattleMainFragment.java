package com.example.cpe436.rpgme.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.Values;
import com.example.cpe436.rpgme.model.Character;
import com.example.cpe436.rpgme.model.CharacterClass;
import com.example.cpe436.rpgme.model.Monster;
import com.example.cpe436.rpgme.model.MonsterSpecies;
import com.example.cpe436.rpgme.model.Skill;
import com.example.cpe436.rpgme.model.Sprite;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Kayla on 5/21/2016.
 */
public class BattleMainFragment extends MyFragment {

    // Displays character sprite + basic info
    private CharacterInfoFragment characterInfoFragment;
    private Character character;

    // Display monster info
    private Monster monster;
    private ImageView imgMonster;
    private TextView txtMonsterName;
    private TextView txtMonsterHP;

    // Attack skills
    private Button skill1;
    private Button skill2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        character = Character.getCharacterInstance(getActivity());
        characterInfoFragment = new CharacterInfoFragment();
        characterInfoFragment.setFullSprite(false);

        skill1 = (Button) fragmentView.findViewById(R.id.battle_skill1);
        skill2 = (Button) fragmentView.findViewById(R.id.battle_skill2);

        imgMonster = (ImageView) fragmentView.findViewById(R.id.monster_image);
        txtMonsterName = (TextView) fragmentView.findViewById(R.id.monster_name);
        txtMonsterHP = (TextView) fragmentView.findViewById(R.id.monster_hp);

        if (savedInstanceState == null) {
            monster = new Monster(character.getLevel());
            displayMonsterInfo();
        }
        setSkills();

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_character_info);
        if (fragment == null) {
            transaction.add(R.id.content_character_info, characterInfoFragment);
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the monster info
        outState.putInt("SPECIES", Arrays.asList(MonsterSpecies.values()).indexOf(monster.getSpecies()));
        outState.putInt("LEVEL", monster.getLevel());
        outState.putInt("HP", monster.getCurHP());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore the monster info
            MonsterSpecies species = MonsterSpecies.values()[savedInstanceState.getInt("SPECIES")];
            int level = savedInstanceState.getInt("LEVEL");
            int hp = savedInstanceState.getInt("HP");
            monster = new Monster(species, level);
            monster.setCurHP(hp);
            displayMonsterInfo();
        }
    }

    private void displayMonsterInfo() {
        // Animate the sprite
        imgMonster.setImageResource(monster.getImage());
        AnimationDrawable frameAnimation = (AnimationDrawable) imgMonster.getDrawable();
        frameAnimation.start();

        Resources res = getResources();
        txtMonsterName.setText("LV " + monster.getLevel() + " " + monster.getName());
        txtMonsterHP.setText(String.format(res.getString(R.string.stat_hp), monster.getCurHP(), monster.getMaxHP()));
    }

    private void setSkills() {
        CharacterClass characterClass = character.getCharacterClass();
        final Skill s1 = characterClass.getSkill1();
        final Skill s2 = characterClass.getSkill2();

        skill1.setCompoundDrawablesWithIntrinsicBounds(s1.getColorImage(), 0, 0, 0);
        skill1.setText(s1.getName() + "(" + s1.getMP() + " MP)");
        skill2.setCompoundDrawablesWithIntrinsicBounds(s2.getColorImage(), 0, 0, 0);
        skill2.setText(s2.getName() + "(" + s2.getMP() + " MP)");

        skill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s1.getMP() > character.getCurMP()) {
                    Toast.makeText(parent, "You don't have enough MP!", Toast.LENGTH_LONG).show();
                    return;
                }
                playerAttack(s1);
                monsterAttack();
            }
        });
        skill2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s2.getMP() > character.getCurMP()) {
                    Toast.makeText(parent, "You don't have enough MP!", Toast.LENGTH_LONG).show();
                    return;
                }
                playerAttack(s2);
                monsterAttack();
            }
        });
    }

    private void playerAttack(Skill skill) {
        // Battle is already over
        if (monster.getCurHP() == 0 || character.getCurHP() == 0) {
            return;
        }
        character.setCurMP(character.getCurMP() - skill.getMP(), parent);

        int damage = useSkill(skill);
        monster.setCurHP(monster.getCurHP() - damage);
        if (monster.getCurHP() == 0) {
            battleWon();
        }

        characterInfoFragment.update();
        displayMonsterInfo();
    }

    private int useSkill(Skill skill) {
        int def = 1;
        int atk = (int) (character.getAttack() * skill.getAttackScale() +
                character.getMagicAttack() * skill.getMagicScale());

        // Attack; deal physical damage
        if (skill == Skill.ATTACK) {
            def = monster.getDefense();
        }
        // Magic; deal magic damage
        else if (skill == Skill.MAGIC) {
            def = monster.getMagicDefense();
        }
        // Defend; take half damage next turn
        else if (skill == Skill.DEFEND) {
            //TODO
        }
        // Bandage; heal 5% of hp
        else if (skill == Skill.BANDAGE) {
            int heal = (int) (character.getCurHP() * 0.05);
            character.setCurHP(character.getCurHP() + heal, parent);
        }

        return (int) (atk / (def * 0.2));
    }

    private void monsterAttack() {
        // Battle is already over
        if (monster.getCurHP() == 0 || character.getCurHP() == 0) {
            return;
        }

        // Decide which attack the monster uses
        int rand = new Random().nextInt(monster.getInt() + monster.getStr());
        boolean usedMagic = rand < monster.getInt();
        int atk = usedMagic ? monster.getMagicAttack() : monster.getAttack();
        int def = usedMagic ? character.getMagicDefense() : character.getDefense();
        int damage = (int) (atk / (def * 0.2));
        character.setCurHP(character.getCurHP() - damage, parent);
        characterInfoFragment.update();

        if (character.getCurHP() == 0) {
            battleLost();
        }
    }

    private void battleWon() {
        // Show the popup dialog
        final View dialogView = View.inflate(parent, R.layout.dialog_battle_won, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(parent).create();
        alertDialog.setView(dialogView);
        alertDialog.show();

        int gold = monster.getLevel() * 10 + new Random().nextInt(monster.getLevel() + 1);
        TextView reward = (TextView) dialogView.findViewById(R.id.battle_reward);
        reward.setText(String.format(getString(R.string.battle_reward), gold));
        character.giveGold(gold, parent);

        dialogView.findViewById(R.id.button_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void battleLost() {
        // Show the popup dialog
        final View dialogView = View.inflate(parent, R.layout.dialog_battle_lost, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(parent).create();
        alertDialog.setView(dialogView);
        alertDialog.show();

        ImageView imageView = (ImageView) dialogView.findViewById(R.id.battle_monster);
        imageView.setImageResource(monster.getImage());
        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getDrawable();
        frameAnimation.start();

        dialogView.findViewById(R.id.button_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
}