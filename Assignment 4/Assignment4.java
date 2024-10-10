import java.io.*;
import java.util.*;

/**
 * The type Main.
 */
public class Main {
    /**
     * The type Board.
     */
    static class Board {
        private Map<String, BoardEntity> boardData = new HashMap<>();
        private ArrayList<Insect> insects;
        private int size;

        /**
         * Instantiates a new Board.
         *
         * @param boardSize the board size
         * @param insects   the insects
         */
        public Board(int boardSize, ArrayList<Insect> insects) {
            this.size = boardSize;
            this.insects = insects;
        }

        /**
         * Add entity.
         *
         * @param entity the entity
         */
        public void addEntity(BoardEntity entity) {
            boardData.put(entity.getPosition().getStrPosition(), entity);
        }

        /**
         * Gets entity.
         *
         * @param position the position
         * @return the entity
         */
        public BoardEntity getEntity(EntityPosition position) {
            return boardData.get(position.getStrPosition());
        }

        /**
         * Gets direction.
         *
         * @param insect the insect
         * @return the direction
         */
        public Direction getDirection(Insect insect) {
            return insect.getBestDirection(boardData, size);
        }

        /**
         * Gets board data.
         *
         * @return the board data
         */
        public Map<String, BoardEntity> getBoardData() {
            return boardData;
        }

        /**
         * Gets size.
         *
         * @return the size
         */
        public int getSize() {
            return size;
        }

        /**
         * Gets direction sum.
         *
         * @param insect the insect
         * @return the direction sum
         */
        public int getDirectionSum(Insect insect) {
            return 1;
        }
    }

    /**
     * The type Board entity.
     */
    abstract static class BoardEntity {
        /**
         * The constant entityPosition.
         */
        protected static EntityPosition entityPosition;

        /**
         * Gets position.
         *
         * @return the position
         */
        public EntityPosition getPosition() {
            return entityPosition;
        }
    }

    /**
     * The type Entity position.
     */
    static class EntityPosition {
        private int x;
        private int y;

        /**
         * Instantiates a new Entity position.
         *
         * @param x the x
         * @param y the y
         */
        public EntityPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Gets str position.
         *
         * @return the str position
         */
        public String getStrPosition() {
            return String.format("%d %d", x, y);
        }

        /**
         * Gets x.
         *
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * Gets y.
         *
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * Sets position.
         *
         * @param x the x
         * @param y the y
         */
        public void setPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * The enum Insect color.
     */
    public enum InsectColor {
        /**
         * Red insect color.
         */
        RED,
        /**
         * Green insect color.
         */
        GREEN,
        /**
         * Blue insect color.
         */
        BLUE,
        /**
         * Yellow insect color.
         */
        YELLOW;

        /**
         * To color insect color.
         *
         * @param s the s
         * @return the insect color
         */
        public static InsectColor toColor(String s) {
            return Arrays.stream(InsectColor.values())
                    .filter(color -> color.name().equalsIgnoreCase(s))
                    .findFirst()
                    .orElse(null);
        }

        /**
         * To str string.
         *
         * @param color the color
         * @return the string
         */
        public static String toStr(InsectColor color){
            if (color == InsectColor.RED){
                return "Red";
            }
            if (color == InsectColor.GREEN){
                return "Green";
            }
            if (color == InsectColor.BLUE){
                return "Blue";
            }
            if (color == InsectColor.YELLOW){
                return "Yellow";
            }
            return null;
        }
    }

    /**
     * The enum Direction.
     */
    public enum Direction {
        /**
         * N direction.
         */
        N("North"),
        /**
         * E direction.
         */
        E("East"),
        /**
         * S direction.
         */
        S("South"),
        /**
         * W direction.
         */
        W("West"),
        /**
         * Ne direction.
         */
        NE("North-East"),
        /**
         * Se direction.
         */
        SE("South-East"),
        /**
         * Sw direction.
         */
        SW("South-West"),
        /**
         * Nw direction.
         */
        NW("North-West");
        private final String textRepresentation;
        private Direction(String text) {
            this.textRepresentation = text;
        }

        /**
         * Gets text representation.
         *
         * @return the text representation
         */
        public String getTextRepresentation() {
            return textRepresentation;
        }
    }

    /**
     * The type Food point.
     */
    static class FoodPoint extends BoardEntity {
        /**
         * The Value.
         */
        protected int value;

        /**
         * Instantiates a new Food point.
         *
         * @param position the position
         * @param value    the value
         */
        public FoodPoint(EntityPosition position, int value) {
            entityPosition = position;
            this.value = value;
        }

        /**
         * Gets value.
         *
         * @return the value
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * The type Insect.
     */
    abstract static class Insect extends BoardEntity {
        /**
         * The Color.
         */
        protected InsectColor color;
        private int Food;

        /**
         * Instantiates a new Insect.
         *
         * @param position the position
         * @param color    the color
         */
        public Insect(EntityPosition position, InsectColor color) {
            entityPosition = position;
            this.color = color;
        }

        /**
         * Gets best direction.
         *
         * @param boardData the board data
         * @param boardSize the board size
         * @return the best direction
         */
        public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
            Map<Integer, Direction> foodDir = new HashMap<>();
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            for (Direction dir : Direction.values()) {
                int curFood = 0;
                if (dir == Direction.NW || dir == Direction.W || dir == Direction.SW) {
                    updatePositionAndFood(boardData, curFood, dir, boardSize, foodDir);
                } else if (dir == Direction.N || dir == Direction.E || dir == Direction.S) {
                    updatePositionAndFood(boardData, curFood, dir, boardSize, foodDir);
                } else {
                    updatePositionAndFood(boardData, curFood, dir, boardSize, foodDir);
                }
                entityPosition.setPosition(originalX, originalY);
            }
            return foodDir.entrySet().stream()
                    .max(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .orElse(null);
        }

        /**
         * Update position and food.
         *
         * @param boardData the board data
         * @param curFood   the cur food
         * @param dir       the dir
         * @param limit     the limit
         * @param foodDir   the food dir
         */
        void updatePositionAndFood(Map<String, BoardEntity> boardData, int curFood, Direction dir, int limit, Map<Integer, Direction> foodDir) {
            for (int i = 0; i < limit; i++) {
                int nextX = entityPosition.x;
                int nextY = entityPosition.y;
                switch (dir) {
                    case N:
                        nextY += i;
                        break;
                    case NE:
                        nextX += i;
                        nextY += i;
                        break;
                    case NW:
                        nextX -= i;
                        nextY += i;
                        break;
                    case S:
                        nextY -= i;
                        break;
                    case SE:
                        nextX += i;
                        nextY -= i;
                        break;
                    case SW:
                        nextX -= i;
                        nextY -= i;
                        break;
                    case E:
                        nextX += i;
                        break;
                    case W:
                        nextX -= i;
                        break;
                }
                String nextPos = String.format("%d %d", nextX, nextY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(nextX, nextY);
                        curFood += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    }
                } else {
                    entityPosition.setPosition(nextX, nextY);
                }
            }
            foodDir.put(curFood, dir);
        }

        /**
         * Travel direction int.
         *
         * @param dir       the dir
         * @param boardData the board data
         * @param boardSize the board size
         * @return the int
         */
        public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
            int curFood;
            if (dir == Direction.NW || dir == Direction.W || dir == Direction.SW) {
                curFood = travelInDirection(boardData, dir, boardSize);
            } else if (dir == Direction.N || dir == Direction.E || dir == Direction.S) {
                curFood = travelInDirection(boardData, dir, boardSize);
            } else {
                curFood = travelInDirection(boardData, dir, boardSize);
            }
            this.Food = curFood;
            return curFood;
        }

        /**
         * Travel in direction int.
         *
         * @param boardData the board data
         * @param dir       the dir
         * @param limit     the limit
         * @return the int
         */
        int travelInDirection(Map<String, BoardEntity> boardData, Direction dir, int limit) {
            int curFood = 0;
            for (int i = Math.min(entityPosition.x, entityPosition.y); i < limit; i++) {
                int difX = limit - entityPosition.x;
                int difY = limit - entityPosition.y;
                int nextX = entityPosition.x;
                int nextY = entityPosition.y;
                switch (dir) {
                    case N:
                        nextY += i;
                        break;
                    case NE:
                        nextX += i;
                        nextY += i;
                        break;
                    case NW:
                        nextX -= i;
                        nextY += i;
                        break;
                    case S:
                        nextY -= i;
                        break;
                    case SE:
                        nextX += i;
                        nextY -= i;
                        break;
                    case SW:
                        nextX -= i;
                        nextY -= i;
                        break;
                    case E:
                        nextX += i;
                        break;
                    case W:
                        nextX -= i;
                        break;
                }
                String nextPos = String.format("%d %d", nextX, nextY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    entityPosition.setPosition(nextX, nextY);
                    if (creature instanceof FoodPoint) {
                        curFood += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    } else {
                        boardData.remove(entityPosition.getStrPosition());
                    }
                } else {
                    entityPosition.setPosition(nextX, nextY);
                }
            }
            return curFood;
        }

        /**
         * Gets color.
         *
         * @return the color
         */
        public InsectColor getColor() {
            return color;
        }

        /**
         * Get food int.
         *
         * @return the int
         */
        public int getFood(){return Food;}
    }

    /**
     * The type Grasshopper.
     */
    static class Grasshopper extends Insect {
        /**
         * Instantiates a new Grasshopper.
         *
         * @param position the position
         * @param color    the color
         */
        public Grasshopper(EntityPosition position, InsectColor color) {
            super(position, color);
        }
        @Override
        public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
            Map<Integer, Direction> foodDir = new HashMap<>();
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;

            for (Direction dir : Arrays.asList(Direction.N, Direction.E, Direction.S, Direction.W)) {
                int curFood = 0;

                updatePositionAndFood(boardData, curFood, dir, boardSize, foodDir);

                entityPosition.setPosition(originalX, originalY);
            }

            return foodDir.entrySet().stream()
                    .max(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .orElse(null);
        }
        @Override
        public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
            int curFood = 0;
            if (isDirectionAllowed(dir)) {
                curFood = travelInDirection(boardData, dir, boardSize);
            }
            return curFood;
        }
        private boolean isDirectionAllowed(Direction dir) {
            return Arrays.asList(Direction.N, Direction.E, Direction.S, Direction.W).contains(dir);
        }
    }

    /**
     * The interface Orthogonal moving.
     */
    interface OrthogonalMoving {
        /**
         * Gets orthogonal direction visible value.
         *
         * @param dir            the dir
         * @param entityPosition the entity position
         * @param boardData      the board data
         * @param boardSize      the board size
         * @return the orthogonal direction visible value
         */
        int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize);

        /**
         * Travel orthogonal int.
         *
         * @param dir            the dir
         * @param entityPosition the entity position
         * @param color          the color
         * @param boardData      the board data
         * @param boardSize      the board size
         * @return the int
         */
        int travelOrthogonal(Direction dir, EntityPosition entityPosition, InsectColor color,
                             Map<String, BoardEntity> boardData, int boardSize);
    }

    /**
     * The interface Diagonal moving.
     */
    interface DiagonalMoving {
        /**
         * Gets diagonal direction visible value.
         *
         * @param dir            the dir
         * @param entityPosition the entity position
         * @param boardData      the board data
         * @param boardSize      the board size
         * @return the diagonal direction visible value
         */
        int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                             Map<String, BoardEntity> boardData, int boardSize);

        /**
         * Travel diagonal int.
         *
         * @param dir            the dir
         * @param entityPosition the entity position
         * @param color          the color
         * @param boardData      the board data
         * @param boardSize      the board size
         * @return the int
         */
        int travelDiagonal(Direction dir, EntityPosition entityPosition, InsectColor color,
                           Map<String, BoardEntity> boardData, int boardSize);
    }

    /**
     * The type Butterfly.
     */
    static class Butterfly extends Insect implements OrthogonalMoving {
        public Butterfly(EntityPosition entityPosition, InsectColor color) {
            super(entityPosition, color);
        }
        public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
            int maxFood = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;
            switch (dir) {
                case N:
                    limit = boardSize - entityPosition.x;
                    break;
                case S:
                    limit = entityPosition.x + 1;
                    break;
                case E:
                    limit = boardSize - entityPosition.y;
                    break;
                case W:
                    limit = entityPosition.y + 1;
                    break;
                default:
                    return maxFood;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;
                switch (dir) {
                    case N:
                        curX += i;
                        break;
                    case S:
                        curX -= i;
                        break;
                    case E:
                        curY += i;
                        break;
                    case W:
                        curY -= i;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        maxFood = Math.max(maxFood, ((FoodPoint) creature).getValue());
                        boardData.remove(nextPos, creature);
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return maxFood;
        }
        public int travelOrthogonal(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
            int food = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;
            switch (dir) {
                case N:
                    limit = boardSize - entityPosition.x;
                    break;
                case S:
                    limit = entityPosition.x + 1;
                    stepSize = -1;
                    break;
                case E:
                    limit = boardSize - entityPosition.y;
                    break;
                case W:
                    limit = entityPosition.y + 1;
                    stepSize = -1;
                    break;
                default:
                    return food;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;
                switch (dir) {
                    case N:
                    case S:
                        curX += i * stepSize;
                        break;
                    case E:
                    case W:
                        curY += i * stepSize;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        food += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    } else if (creature instanceof Insect) {
                        entityPosition.setPosition(curX, curY);
                        boardData.remove(entityPosition.getStrPosition());
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return food;
        }
    }

    /**
     * The type Ant.
     */
    static class Ant extends Insect implements OrthogonalMoving, DiagonalMoving {
        public Ant(EntityPosition entityPosition, InsectColor color) {
            super(entityPosition, color);
        }
        public int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
            int maxFood = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;
            switch (dir) {
                case N:
                    limit = boardSize - entityPosition.x;
                    break;
                case S:
                    limit = entityPosition.x + 1;
                    break;
                case E:
                    limit = boardSize - entityPosition.y;
                    break;
                case W:
                    limit = entityPosition.y + 1;
                    break;
                default:
                    return maxFood;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;
                switch (dir) {
                    case N:
                        curX += i;
                        break;
                    case S:
                        curX -= i;
                        break;
                    case E:
                        curY += i;
                        break;
                    case W:
                        curY -= i;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        maxFood = Math.max(maxFood, ((FoodPoint) creature).getValue());
                        boardData.remove(nextPos, creature);
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return maxFood;
        }
        public int travelOrthogonal(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
            int food = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;
            switch (dir) {
                case N:
                    limit = boardSize - entityPosition.x;
                    break;
                case S:
                    limit = entityPosition.x + 1;
                    stepSize = -1;
                    break;
                case E:
                    limit = boardSize - entityPosition.y;
                    break;
                case W:
                    limit = entityPosition.y + 1;
                    stepSize = -1;
                    break;
                default:
                    return food;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;
                switch (dir) {
                    case N:
                    case S:
                        curX += i * stepSize;
                        break;
                    case E:
                    case W:
                        curY += i * stepSize;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        food += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    } else if (creature instanceof Insect) {
                        entityPosition.setPosition(curX, curY);
                        boardData.remove(entityPosition.getStrPosition());
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return food;
        }
        public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
            int maxFood = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;

            switch (dir) {
                case NW:
                    limit = Math.min(boardSize - entityPosition.x, boardSize - entityPosition.y);
                    break;
                case NE:
                    limit = Math.min(boardSize - entityPosition.x, entityPosition.y + 1);
                    break;
                case SW:
                    limit = Math.min(entityPosition.x + 1, boardSize - entityPosition.y);
                    stepSize = -1;
                    break;
                case SE:
                    limit = Math.min(entityPosition.x + 1, entityPosition.y + 1);
                    stepSize = -1;
                    break;
                default:
                    return maxFood;
            }

            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;

                switch (dir) {
                    case NW:
                        curX += i;
                        curY += i;
                        break;
                    case NE:
                        curX += i;
                        curY -= i;
                        break;
                    case SW:
                        curX -= i;
                        curY += i * stepSize;
                        break;
                    case SE:
                        curX -= i;
                        curY -= i * stepSize;
                        break;
                }

                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        maxFood = Math.max(maxFood, ((FoodPoint) creature).getValue());
                        boardData.remove(nextPos, creature);
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }

            entityPosition.setPosition(originalX, originalY);
            return maxFood;
        }
        public int travelDiagonal(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
            int food = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;

            switch (dir) {
                case NW:
                    limit = Math.min(boardSize - entityPosition.x, boardSize - entityPosition.y);
                    break;
                case NE:
                    limit = Math.min(boardSize - entityPosition.x, entityPosition.y + 1);
                    break;
                case SW:
                    limit = Math.min(entityPosition.x + 1, boardSize - entityPosition.y);
                    stepSize = -1;
                    break;
                case SE:
                    limit = Math.min(entityPosition.x + 1, entityPosition.y + 1);
                    stepSize = -1;
                    break;
                default:
                    return food;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;

                switch (dir) {
                    case NW:
                        curX += i;
                        curY += i;
                        break;
                    case NE:
                        curX += i;
                        curY -= i;
                        break;
                    case SW:
                        curX -= i;
                        curY += i * stepSize;
                        break;
                    case SE:
                        curX -= i;
                        curY -= i * stepSize;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        food += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    } else if (creature instanceof Insect) {
                        entityPosition.setPosition(curX, curY);
                        boardData.remove(entityPosition.getStrPosition());
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return food;
        }
    }

    /**
     * The type Spider.
     */
    static class Spider extends Insect implements DiagonalMoving {
        public Spider(EntityPosition position, InsectColor color) {
            super(position, color);
        }
        public int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition, Map<String, BoardEntity> boardData, int boardSize) {
            int maxFood = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;

            switch (dir) {
                case NW:
                    limit = Math.min(boardSize - entityPosition.x, boardSize - entityPosition.y);
                    break;
                case NE:
                    limit = Math.min(boardSize - entityPosition.x, entityPosition.y + 1);
                    break;
                case SW:
                    limit = Math.min(entityPosition.x + 1, boardSize - entityPosition.y);
                    stepSize = -1;
                    break;
                case SE:
                    limit = Math.min(entityPosition.x + 1, entityPosition.y + 1);
                    stepSize = -1;
                    break;
                default:
                    return maxFood;
            }

            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;

                switch (dir) {
                    case NW:
                        curX += i;
                        curY += i;
                        break;
                    case NE:
                        curX += i;
                        curY -= i;
                        break;
                    case SW:
                        curX -= i;
                        curY += i * stepSize;
                        break;
                    case SE:
                        curX -= i;
                        curY -= i * stepSize;
                        break;
                }

                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        maxFood = Math.max(maxFood, ((FoodPoint) creature).getValue());
                        boardData.remove(nextPos, creature);
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }

            entityPosition.setPosition(originalX, originalY);
            return maxFood;
        }
        public int travelDiagonal(Direction dir, EntityPosition entityPosition, InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
            int food = 0;
            int originalX = entityPosition.x;
            int originalY = entityPosition.y;
            int stepSize = 1;
            int limit;
            switch (dir) {
                case NW:
                    limit = Math.min(boardSize - entityPosition.x, boardSize - entityPosition.y);
                    break;
                case NE:
                    limit = Math.min(boardSize - entityPosition.x, entityPosition.y + 1);
                    break;
                case SW:
                    limit = Math.min(entityPosition.x + 1, boardSize - entityPosition.y);
                    stepSize = -1;
                    break;
                case SE:
                    limit = Math.min(entityPosition.x + 1, entityPosition.y + 1);
                    stepSize = -1;
                    break;
                default:
                    return food;
            }
            for (int i = 0; i < limit; i++) {
                int curX = entityPosition.x;
                int curY = entityPosition.y;

                switch (dir) {
                    case NW:
                        curX += i;
                        curY += i;
                        break;
                    case NE:
                        curX += i;
                        curY -= i;
                        break;
                    case SW:
                        curX -= i;
                        curY += i * stepSize;
                        break;
                    case SE:
                        curX -= i;
                        curY -= i * stepSize;
                        break;
                }
                String nextPos = String.format("%d %d", curX, curY);
                if (boardData.containsKey(nextPos)) {
                    BoardEntity creature = boardData.get(nextPos);
                    if (creature instanceof FoodPoint) {
                        entityPosition.setPosition(curX, curY);
                        food += ((FoodPoint) creature).getValue();
                        boardData.remove(nextPos, creature);
                    } else if (creature instanceof Insect) {
                        entityPosition.setPosition(curX, curY);
                        boardData.remove(entityPosition.getStrPosition());
                    }
                } else {
                    entityPosition.setPosition(curX, curY);
                }
            }
            entityPosition.setPosition(originalX, originalY);
            return food;
        }
    }

    /**
     * The type Invalid board size exception.
     */
    static class InvalidBoardSizeException extends Exception {
        public String getMessage() {
            return "Invalid board size";
        }
    }

    /**
     * The type Invalid number of insects exception.
     */
    static class InvalidNumberOfInsectsException extends Exception {
        public String getMessage() {
            return "Invalid number of insects";
        }
    }

    /**
     * The type Invalid number of food points exception.
     */
    static class InvalidNumberOfFoodPointsException extends Exception {
        public String getMessage() {
            return "Invalid number of food points";
        }
    }

    /**
     * The type Invalid insect color exception.
     */
    static class InvalidInsectColorException extends Exception {
        public String getMessage() {
            return "Invalid insect color";
        }
    }

    /**
     * The type Invalid insect type exception.
     */
    static class InvalidInsectTypeException extends Exception {
        public String getMessage() {
            return "Invalid insect type";
        }
    }
    private static Board gameBoard(ArrayList<Insect> insects, Map<String, BoardEntity> boardData, int boardSize) {
        for (int i = 0;i < insects.size();i++){
            Insect insect = insects.get(i);
            Direction dir = insect.getBestDirection(boardData, boardSize);
            int travelDirection = insect.travelDirection(dir, boardData, boardSize);
            InsectColor color = insect.getColor();
            System.out.println(InsectColor.toStr(color) + " " + insect.getClass().toString().substring(11) + " " + dir.getTextRepresentation() + " " + travelDirection);
        }
        return null;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String inputPath = "C:\\Users\\ilyas\\IdeaProjects\\Assignment 4\\input.txt";
        String outputPath = "C:\\Users\\ilyas\\IdeaProjects\\Assignment 4\\output.txt";
        try (FileInputStream fin = new FileInputStream(inputPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
             PrintStream fout = new PrintStream(new FileOutputStream(outputPath))) {
            System.setOut(fout);
            ArrayList<String> commands = new ArrayList<>();
            ArrayList<String> validColors = new ArrayList<>();
            validColors.add("red");
            validColors.add("green");
            validColors.add("blue");
            validColors.add("yellow");
            ArrayList<String> insectsCommands = new ArrayList<>();
            ArrayList<String> foodCommands = new ArrayList<>();
            ArrayList<Insect> insects = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    commands.add(line);
                } else {
                    throw new InvalidInputsException();
                }
            }
            try {
                int d = Integer.parseInt(commands.get(0));
                int n = Integer.parseInt(commands.get(1));
                int m = Integer.parseInt(commands.get(2));
                if (d < 4 || d > 1000) {
                    throw new InvalidBoardSizeException();
                }
                if (n < 1 || n > 16) {
                    throw new InvalidNumberOfInsectsException();
                }
                if (m < 1 || m > 200) {
                    throw new InvalidNumberOfFoodPointsException();
                }
                for (int i = 3; i < 3 + n; i++) {
                    insectsCommands.add(commands.get(i));
                }
                for (int i = 3 + n; i < 3 + n + m; i++) {
                    foodCommands.add(commands.get(i));
                }
                if (insectsCommands.size() != n){
                    throw new InvalidInputsException();
                }
                if (foodCommands.size() != m){
                    throw new InvalidInputsException();
                }
                Board board = new Board(d, insects);
                for (String insectsCommand : insectsCommands) {
                    String[] data = insectsCommand.split(" ");
                    if (data.length != 4){
                        throw new InvalidInputsException();
                    }
                    String color = data[0];
                    if (!validColors.contains(color.toLowerCase())) {
                        throw new InvalidInsectColorException();
                    }
                    String insect = data[1];
                    int x = Integer.parseInt(data[2]);
                    int y = Integer.parseInt(data[3]);
                    if (x > d | y > d){
                        throw new InvalidEntityPosistionException();
                    }
                    EntityPosition pos = new EntityPosition(x, y);
                    if (board.getEntity(pos) != null) {
                        throw new DuplicateInsectException();
                    }
                    switch (insect.toLowerCase()) {
                        case "grasshopper":
                            Grasshopper grasshopper = new Grasshopper(pos, InsectColor.toColor(color));
                            board.addEntity(grasshopper);
                            insects.add(grasshopper);
                            break;
                        case "butterfly":
                            Butterfly butterfly = new Butterfly(pos, InsectColor.toColor(color));
                            board.addEntity(butterfly);
                            insects.add(butterfly);
                            break;
                        case "ant":
                            Ant ant = new Ant(pos, InsectColor.toColor(color));
                            board.addEntity(ant);
                            insects.add(ant);
                            break;
                        case "spider":
                            Spider spider = new Spider(pos, InsectColor.toColor(color));
                            board.addEntity(spider);
                            insects.add(spider);
                            break;
                        default:
                            throw new InvalidInsectTypeException();
                    }
                }
                for (String foodCommand : foodCommands) {
                    String[] data = foodCommand.split(" ");
                    if (data.length != 3){
                        throw new InvalidInputsException();
                    }
                    int value = Integer.parseInt(data[0]);
                    int x = Integer.parseInt(data[1]);
                    int y = Integer.parseInt(data[2]);
                    EntityPosition pos = new EntityPosition(x, y);
                    if (board.getEntity(pos) != null) {
                        throw new TwoEntitiesOnSamePositionException();
                    }
                    board.addEntity(new FoodPoint(pos, value));
                }
                if (commands.size() - insectsCommands.size() - foodCommands.size() != 3){
                    throw new InvalidInputsException();
                }
                gameBoard(insects, board.getBoardData(), board.getSize());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                System.exit(0);
            } catch (InvalidBoardSizeException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (InvalidNumberOfInsectsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (InvalidNumberOfFoodPointsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (InvalidInsectColorException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (InvalidInsectTypeException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (DuplicateInsectException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (TwoEntitiesOnSamePositionException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            } catch (InvalidEntityPosistionException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

/**
 * The type Invalid entity posistion exception.
 */
class InvalidEntityPosistionException extends Exception {
    public String getMessage() {
        return "Invalid entity position";
    }
}

/**
 * The type Duplicate insect exception.
 */
class DuplicateInsectException extends Exception {
    public String getMessage() {
        return "Duplicate insects";
    }
}

/**
 * The type Two entities on same position exception.
 */
class TwoEntitiesOnSamePositionException extends Exception {
    public String getMessage() {
        return "Two entities in the same position";
    }
}

/**
 * The type Invalid inputs exception.
 */
class InvalidInputsException extends Exception {
    public String getMessage() {
        return "Invalid inputs";
    }
}
