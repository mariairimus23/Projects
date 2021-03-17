library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity display is
	port (Clk: in std_logic;
		Input: in std_logic_vector(31 downto 0);
		Anod: out std_logic_vector(3 downto 0);
		Catod: out std_logic_vector(7 downto 0));
end display;

architecture Behavioral of display is
signal HEX: std_logic_vector(7 downto 0);
signal S: std_logic_vector(15 downto 0) := "0000000000000000";

begin

--counter up
process(clk)
begin
if clk'event and clk='1' then
    S <= S+1;
end if;
end process;

--primul mux 4:1
process(S, Input)
begin
    case S(15 downto 14) is
        when "00" => HEX<= Input(7 downto 0);
        when "01" => HEX<= Input(15 downto 8);
        when "10" => HEX<= Input(23 downto 16);
        when others => HEX<= Input(31 downto 24);
    end case;
end process;

--hex to 7 seg dcd
with HEX SELect
   catod <=  "11000000" when "00110000",   --0
	         "11111001" when "00110001",   --1
             "10100100" when "00110010",   --2
             "10110000" when "00110011",   --3
             "10011001" when "00110100",   --4
             "10010010" when "00110101",   --5
             "10000010" when "00110110",   --6
             "11111000" when "00110111",   --7
             "10000000" when "00111000",   --8
             "10010000" when "00111001",   --9

             "10001000" when "01000001",   --A
             "10000011" when "01000010",   --b
             "10100111" when "01000011",   --c
             "10100001" when "01000100",   --d
             "10000110" when "01000101",   --E
             "10001110" when "01000110",   --F
             "11000010" when "01000111",   --G
	         "10001001" when "01001000",   --H
             "11101110" when "01001001",   --i 
             "11100001" when "01001010",   --j
             "10001010" when "01001011",   --k 
             "11000111" when "01001100",   --L
             "10101010" when "01001101",   --m
             "10101011" when "01001110",   --n
             "10100011" when "01001111",   --o
             "10001100" when "01010000",   --P
             "10011000" when "01010001",   --q
             "10101111" when "01010010",   --r
             "11010010" when "01010011",   --S
             "10000111" when "01010100",   --t
             "11100011" when "01010101",   --u
             "11010101" when "01010110",   --V
             "10010101" when "01010111",   --w
             "11101011" when "01011000",   --x
             "10010001" when "01011001",   --y
             "11100100" when "01011010",   --Z

             "11111101" when "01100000",   -- ' (apostrof)
             "10111111" when "00101101",   -- - (minus)
             "10110111" when "00111101",   -- = (egal)
             "11000110" when "01011011",   -- [
             "11110000" when "01011101",   -- ]
             "11101101" when "01011100",   -- \
             "11010011" when "00111011",   -- ;
             "11011111" when "00100111",   -- ' (tilda, sub esc)
             "11110011" when "00101100",   -- ,
             "11111110" when "00101110",   -- .
             "11011011" when "00101111",   -- /
        
             "11111111" when "00100000",    -- (space)
             "11111111" when "00001101",    -- (enter, cr)
             "11111111" when "00001000",    -- (backspace)
             "11111111" when others ;
 
--al doilea mux 4:1        
process(S)
begin
    case S(15 downto 14) is
        when "00" => anod <= "1110";
        when "01" => anod <= "1101";
        when "10" => anod <= "1011";
        when others => anod <= "0111";
    end case;
end process;
         
end Behavioral;