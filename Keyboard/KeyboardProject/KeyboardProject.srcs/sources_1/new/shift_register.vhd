library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Registru de shiftare cu rst si pl asincron, si clock pe falling edge.

entity shift_register is
    Port ( Clk: in std_logic;
	       Rst: in std_logic;
	       PL: in std_logic;
	       input: in std_logic;
	       enable: out std_logic;
		   Q: out std_logic_vector(10 downto 0));
end shift_register;

architecture Behavioral of shift_register is

signal Q_temp: std_logic_vector(10 downto 0);

begin

process (Clk, Rst)
begin
    if (Rst = '1') then
        Q_temp <= (others => '0');
    elsif (PL = '1') then
        Q_temp <= (others => '1');
    elsif (falling_edge(Clk)) then
	   Q_temp <= input & Q_temp(10 downto 1);
	end if;
end process;
	
Q <= Q_temp;
enable <= not Q_temp(0);

end Behavioral;