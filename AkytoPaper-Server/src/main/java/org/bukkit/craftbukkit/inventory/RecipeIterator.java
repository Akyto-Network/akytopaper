package org.bukkit.craftbukkit.inventory;

import java.util.Iterator;

import org.bukkit.inventory.Recipe;

import net.minecraft.server.CraftingManager;
import net.minecraft.server.IRecipe;
import net.minecraft.server.RecipesFurnace;

public class RecipeIterator implements Iterator<Recipe> {
    private final Iterator<IRecipe> recipes;
    private final Iterator<net.minecraft.server.ItemStack> smeltingCustom;
    private final Iterator<net.minecraft.server.ItemStack> smeltingVanilla;
    private Iterator<?> removeFrom = null;
    private RecipesFurnace recipesFurnace;

    public RecipeIterator(CraftingManager craftingManager, RecipesFurnace recipesFurnace) {
        this.recipes = craftingManager.getRecipes().iterator();
        this.smeltingCustom = recipesFurnace.customRecipes.keySet().iterator();
        this.smeltingVanilla = recipesFurnace.recipes.keySet().iterator();
        recipesFurnace = recipesFurnace;
    }

    public RecipeIterator() {
        this.recipesFurnace = new RecipesFurnace();
        this.recipes = (new CraftingManager()).getRecipes().iterator();
        this.smeltingCustom = this.recipesFurnace.customRecipes.keySet().iterator();
        this.smeltingVanilla = this.recipesFurnace.recipes.keySet().iterator();
    }

    public boolean hasNext() {
        return recipes.hasNext() || smeltingCustom.hasNext() || smeltingVanilla.hasNext();
    }

    public Recipe next() {
        if (recipes.hasNext()) {
            removeFrom = recipes;
            return recipes.next().toBukkitRecipe();
        } else {
            net.minecraft.server.ItemStack item;
            if (smeltingCustom.hasNext()) {
                removeFrom = smeltingCustom;
                item = smeltingCustom.next();
            } else {
                removeFrom = smeltingVanilla;
                item = smeltingVanilla.next();
            }

            CraftItemStack stack = CraftItemStack.asCraftMirror(recipesFurnace.getResult(item));

            return new CraftFurnaceRecipe(stack, CraftItemStack.asCraftMirror(item));
        }
    }

    public void remove() {
        if (removeFrom == null) {
            throw new IllegalStateException();
        }
        removeFrom.remove();
    }
}
