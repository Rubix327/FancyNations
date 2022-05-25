package me.rubix327.fancynations.menu;

import lombok.Getter;
import lombok.Setter;
import me.rubix327.fancynations.data.AbstractDto;
import me.rubix327.fancynations.data.tasks.Task;
import me.rubix327.fancynations.util.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Menu with pages.<br>
 * Supports previous and next buttons.
 */
public abstract class MenuPaginated<T> extends MenuInterlayer {
    private final boolean isInitialized;
    /**
     * Raw elements that should be converted to itemStacks.<br>
     * These can be enumerable objects (Tasks, EntityType, Sound, etc.)
     */
    private List<T> elements;
    /**
     * Items (Material, Name, Amount) ready to be displayed in the menu.
     */
    private List<ItemStack> items;
    /**
     * Slots where the {@link #items} should not be displayed on.<br>
     * The item from {@link #getWrapperItem()} is then placed on <b>empty</b> locked slots.
     */
    private List<Integer> lockedSlots = new ArrayList<>();
    /**
     * Custom slots and their itemStacks.<br>
     * You can define them in {@link #addCustomSlot} method.
     */
    private final TreeMap<Integer, ItemStack> customSlots = new TreeMap<>();
    /**
     * Slots and their raw {@link #elements}.
     */
    @Getter
    private final TreeMap<Integer, T> elementsSlots = new TreeMap<>();
    /**
     * Number of slot in {@link #items} map that is being displayed now.
     */
    private int cursorAt = 0;
    /**
     * Current page opened in the player's menu.
     */
    private int currentPage = 1;
    /**
     * If debug is enabled, player will receive some messages.
     */
    @Getter
    private boolean isDebugEnabled = false;
    /**
     * Defines if the previous and next buttons are showed even if the menu has only one page.
     */
    @Getter @Setter
    private boolean isPrevNextButtonsEnabledNoPages = false;
    /**
     * Contains buttons and their slots.
     */
    private final TreeMap<Integer, Button> buttons = new TreeMap<>();

    public MenuPaginated(Player player){
        super(player);
        init();
        isInitialized = true;
    }

    /**
     * Actions to be taken when opening or updating the menu.<br>
     * It automatically runs when the menu opens. But if you make some changes after calling super()
     * in your child class you must call init() manually in the constructor after all changes.
     */
    protected void init(){
        setElements();
        setItems();
        setElementsSlots();
        if (isInitialized) addPrevNextButtons();
    }

    /**
     * Redraw the menu without moving the cursor to the center.
     */
    protected void updateMenu(){
        resetCursorAt();
        init();
        redraw();
    }

    /**
     * Add previous and next buttons.<br>
     * If no slots are specified, this method must be called only after {@link #setSize} method.
     */
    protected void addPrevNextButtons(){
        if (getMaxPage() > 1 || isPrevNextButtonsEnabledNoPages){
            addPrevNextButtons(getPreviousButtonSlot(), getNextButtonSlot());
        }
    }

    /**
     * Add previous and next buttons with specified slots.
     */
    protected void addPrevNextButtons(int prevSlot, int nextSlot){
        addButton(prevSlot, formPreviousButton(getPreviousItem()));
        addButton(nextSlot, formNextButton(getNextButtonItem()));
    }

    private void setElements(){
        this.elements = getElements();
    }

    private void setItems(){
        this.items = convertToItemStacks(elements);
    }

    /**
     * Get the item material that should be placed on empty locked slots.
     */
    protected Material getWrapperMaterial(){
        return Material.GRAY_STAINED_GLASS_PANE;
    }

    /**
     * <p>Get the item that should be placed on empty locked slots.</p>
     * <p><b>Note that if you override this method, getWrapperMaterial() won't work.</b></p>
     */
    protected ItemStack getWrapperItem(){
        return ItemCreator.of(CompMaterial.fromMaterial(getWrapperMaterial()), "").build().make();
    }

    /**
     * Set the slots where the main elements should not be placed.
     */
    protected void setLockedSlots(Integer... slots){
        lockedSlots.clear();
        lockedSlots.addAll(Arrays.asList(slots));
    }

    /**
     * Set the slots where the main elements only can be placed.
     * Meanwhile, it locks all unspecified slots.
     */
    @SuppressWarnings("BoxingBoxedValue")
    protected void setUnlockedSlots(Integer... slots){
        lockedSlots.clear();
        lockedSlots = IntStream.rangeClosed(0, 53).boxed().collect(Collectors.toList());
        for (Integer slot : slots){
            lockedSlots.remove(Integer.valueOf(slot));
        }
    }

    /**
     * Set the slots where the main elements should not be placed.
     * Figures available: 9x6_bounds, 9x6_circle, 9x6_rows, 9x6_columns, 9x6_two_slots,
     * 9x6_six_slots, 9x3_one_slot, 9x3_bounds, 9x1_one_slot.
     */
    @SuppressWarnings("SameParameterValue")
    protected final void setLockedSlots(String figure){
        switch (figure) {
            case ("9x6_bounds") -> setLockedSlots(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53);
            case ("9x6_circle") -> setUnlockedSlots(12, 13, 14, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 39, 40, 41);
            case ("9x6_rows") -> setLockedSlots(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
            case ("9x6_columns") -> setLockedSlots(0, 8, 9, 18, 27, 36, 45, 17, 26, 35, 44, 53);
            case ("9x6_two_slots") -> setUnlockedSlots(22, 31);
            case ("9x6_six_slots") -> setUnlockedSlots(21, 22, 23, 30, 31, 32);
            case ("9x3_one_slot") -> setUnlockedSlots(13);
            case ("9x3_bounds") -> setUnlockedSlots(10, 11, 12, 13, 14, 15, 16);
            case ("9x1_one_slot") -> setUnlockedSlots(4);
            default -> new ArrayList<>();
        }
    }

    /**
     * Get the slot where the previous button should pe placed on.
     * By default, far left bottom is used.
     */
    protected int getPreviousButtonSlot(){
        return switch (getSize()) {
            case (9) -> 0;
            case (18) -> 9;
            case (27) -> 18;
            case (36) -> 27;
            case (45) -> 36;
            default -> 45;
        };
    }

    /**
     * Get the itemStack that the previous button should have.
     * Default: Material: Spectral_arrow, Name: "&7Previous page"
     */
    protected ItemStack getPreviousItem(){
        return ItemCreator.of(CompMaterial.SPECTRAL_ARROW, "&7Previous page").build().make();
    }

    /**
     * Create the previous button itemStack.
     */
    private Button formPreviousButton(ItemStack itemStack){
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                if (currentPage <= 1) {
                    CompSound.VILLAGER_NO.play(getViewer());
                    return;
                }
                currentPage -= 1;
                resetCursorAt();
                redraw();
                MenuUtil.Play.CLICK_LOW(player);
            }

            @Override
            public ItemStack getItem() {
                return itemStack;
            }
        };
    }

    /**
     * Get the slot where the next button should pe placed on.
     * By default, far right bottom is used.
     */
    protected int getNextButtonSlot(){
        return switch (getSize()) {
            case (9) -> 8;
            case (18) -> 17;
            case (27) -> 26;
            case (36) -> 35;
            case (45) -> 44;
            default -> 53;
        };
    }

    /**
     * Get the itemStack that the next button should have.
     * Default: Material: Tipped_arrow, Name: "&7Next page"
     */
    protected ItemStack getNextButtonItem(){
        return ItemCreator.of(
                CompMaterial.fromMaterial(Material.TIPPED_ARROW), "&7Next page").build().make();
    }

    /**
     * Create the next button itemStack.
     */
    private Button formNextButton(ItemStack itemStack){
        return new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                if (currentPage >= getMaxPage()) {
                    CompSound.VILLAGER_NO.play(getViewer());
                    return;
                }
                currentPage += 1;
                resetCursorAt();
                redraw();
                MenuUtil.Play.CLICK_HIGH(player);
            }

            @Override
            public ItemStack getItem() {
                return itemStack;
            }
        };
    }

    /**
     * Get the amount of unlocked slots.
     */
    private int getAvailableSlotsSize(){
        return getSize() - lockedSlots.size();
    }

    /**
     * Get the number of pages that can be in the menu considering amount of elements and available slots.
     */
    protected final int getMaxPage(){
        float a = (float)items.size() / getAvailableSlotsSize();
        return (a % 2 == 0 ? (int)a : (int)a + 1);
    }

    /**
     * Set the slots for the elements in following order:
     * custom slots, wrapper slots, elements slots
     */
    private void setElementsSlots(){
        Logger.info("setElementsSlots " + toIds(elements));

        elementsSlots.clear();
        for (T element : elements){
            loopSlots(element);
        }
    }

    private void loopSlots(T element){
        for (int page = 1; page <= getMaxPage(); page++){
            for (int slot = 0; slot < getSize(); slot++){
                int finalSlot = (page - 1) * getSize() + slot;

                if (customSlots.containsKey(slot)) continue;
                if (lockedSlots.contains(slot)) continue;
                if (elementsSlots.containsKey(finalSlot)) continue;

                elementsSlots.put(finalSlot, element);
                return;
            }
        }
    }

    private void resetCursorAt() {
        this.cursorAt = (currentPage - 1) * getAvailableSlotsSize();
    }

    @SuppressWarnings("unused")
    protected void addCustomSlot(Integer slot, ItemStack item){
        customSlots.put(slot, item);
    }

    /**
     * Display items on their slots.
     * This method already has a good working implementation, so try not to override it.
     */
    @Override
    public ItemStack getItemAt(int slot){
        if (buttons.containsKey(slot)){
            return buttons.get(slot).getItem();
        }
        if (customSlots.containsKey(slot)){
            return customSlots.get(slot);
        }
        if (lockedSlots.contains(slot)){
            return getWrapperItem();
        }
        else{
            if (cursorAt >= items.size()) return null;
            ItemStack item = items.get(cursorAt);
            cursorAt += 1;
            return item;
        }
    }

    @Override
    protected void onMenuClick(Player player, int slot, InventoryAction action, ClickType click, ItemStack cursor, ItemStack clicked, boolean cancelled) {
        if (clicked.getType().isAir()) return;
        int pagedSlot = slot + (currentPage - 1) * getSize();
        boolean isElement = elementsSlots.containsKey(pagedSlot);

        if (isElement){
            onElementClick(player,  elementsSlots.get(pagedSlot), slot, click);
        }

        if (buttons.containsKey(slot)){
            buttons.get(slot).onClickedInMenu(player, this, click);
        }

        if (isDebugEnabled) {
            tell("Slot: " + slot + ", paged slot: " + pagedSlot + ", element id: " +
                    (isElement ? ((AbstractDto) elementsSlots.get(pagedSlot)).getId() : "no"));
            Logger.info(toIds(elementsSlots));
        }
    }

    protected void addButton(Integer slot, Button btn){
        buttons.put(slot, btn);
    }

    /**
     * Enable sending some debugging messages to the player and to the console.
     */
    protected final void enableDebug(){
        this.isDebugEnabled = true;
    }

    private List<Integer> toIds(List<T> list){
        return list.stream().map(e -> ((Task)e).getId()).toList();
    }

    private TreeMap<Integer, Integer> toIds(TreeMap<Integer, T> elementsSlots){
        TreeMap<Integer, Integer> newMap = new TreeMap<>();
        for (Map.Entry<Integer, T> slot : elementsSlots.entrySet()){
            newMap.put(slot.getKey(), ((AbstractDto)slot.getValue()).getId());
        }
        return newMap;
    }

    /**
     * Actions that should be executed when player clicks on list element.
     * It does not work on all locked slots and custom slots.
     */
    @SuppressWarnings("unused")
    protected void onElementClick(Player player, T object, int slot, ClickType clickType) {}

    /**
     * Convert the elements to itemStacks that should be displayed in the menu.
     */
    private List<ItemStack> convertToItemStacks(List<T> elements){
        return elements.stream().map(this::convertToItemStack).toList();
    }

    /**
     * Get elements that should be converted to itemStacks.
     */
    protected abstract List<T> getElements();

    /**
     * Convert each element to itemStack which should be displayed in the menu.
     */
    protected abstract ItemStack convertToItemStack(T element);
}
