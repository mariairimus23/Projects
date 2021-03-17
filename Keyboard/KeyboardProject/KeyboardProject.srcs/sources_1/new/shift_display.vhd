library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity shift_display is
   port (Clk: in std_logic;
         Rst: in std_logic;
         Input: in std_logic_vector(7 downto 0);
         Q: out std_logic_vector(31 downto 0));
end shift_display;

architecture Behavioral of shift_display is
signal temp_Q : std_logic_vector(31 downto 0);
 begin
    process (Clk, Rst, Input)
    begin
        if Rst = '1' then
            temp_Q <= (others => '1');      
        elsif Rising_Edge(Clk) then
            temp_Q(31 downto 24) <= temp_Q(23 downto 16);
            temp_Q(23 downto 16) <= temp_Q(15 downto 8);
            temp_Q(15 downto 8) <= temp_Q(7 downto 0);
            temp_Q(7 downto 0) <= Input;
         end if;
    end process;
    
Q <= temp_Q;

end Behavioral;